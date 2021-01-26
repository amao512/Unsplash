package com.aslnstbk.unsplash.search.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.view.LoadingError
import com.aslnstbk.unsplash.common.presentation.view.ToolbarBuilder
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.image_details.presentation.IMAGE_ID_BUNDLE_KEY
import com.aslnstbk.unsplash.image_details.presentation.ImageDetailsFragment
import com.aslnstbk.unsplash.image_details.presentation.TAG_BUNDLE_KEY
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.main.MainRouter
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.search.data.models.QueryHistory
import com.aslnstbk.unsplash.search.presentation.models.SearchItem
import com.aslnstbk.unsplash.search.presentation.view.SearchListener
import com.aslnstbk.unsplash.search.presentation.view.query.QueryAdapter
import com.aslnstbk.unsplash.search.presentation.view.search_image.SearchImagesAdapter
import com.aslnstbk.unsplash.search.presentation.viewModel.SearchViewModel
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import com.aslnstbk.unsplash.utils.mappers.EMPTY_STRING
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search), SearchListener, ImageClickListener {

    private val searchViewModel: SearchViewModel by viewModel()
    private val mainRouter: MainRouter by inject()
    private val navigation: Navigation by inject()
    private val imageLoader: ImageLoader by inject()
    private val loadingError: LoadingError by inject()

    private val queryAdapter: QueryAdapter by lazy {
        QueryAdapter(searchListener = this)
    }

    private val searchImagesAdapter: SearchImagesAdapter by lazy {
        SearchImagesAdapter(
            imageClickListener = this,
            imageLoader = imageLoader,
            searchListener = this
        )
    }

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarEditText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildToolbar()
        initViews(view)
        initListeners()
        observeLiveData()
        getTagFromBundle()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        toolbarEditText.hide()
    }

    override fun onQueryHistoryDelete(
        queryHistory: QueryHistory,
        position: Int
    ) {
        searchViewModel.deleteSearchHistory(queryHistory)
        queryAdapter.removeFromList(
            queryHistory = queryHistory,
            position = position
        )
    }

    override fun onQueryHistoryClick(query: String) {
        toolbarEditText.setText(query)
        onSearch(query = query)
    }

    override fun onMoreRetryClick() {
        searchImagesAdapter.changeMoreLoadingItem(
            isLoading = true,
            isError = false
        )
        searchViewModel.getMoreImages(query = toolbarEditText.text.toString())
    }

    override fun onImageClick(imageId: String) {
        val imageDetailsFragment = ImageDetailsFragment()
        val args = Bundle()
        args.putString(IMAGE_ID_BUNDLE_KEY, imageId)
        imageDetailsFragment.arguments = args

        navigation.navigate(
            mainRouter.setScreen(
                fragment = imageDetailsFragment,
                isBackStack = true
            )
        )
    }

    private fun getTagFromBundle() {
        val tag: String = arguments?.getString(TAG_BUNDLE_KEY) ?: EMPTY_STRING

        if (tag.isNotBlank()) {
            onSearch(query = tag)
            toolbarEditText.setText(tag)
        }
    }

    private fun initViews(view: View) {
        progressBar = APP_ACTIVITY.findViewById(R.id.activity_main_progress_bar)
        toolbarEditText = APP_ACTIVITY.findViewById(R.id.activity_main_toolbar_edit_text)
        toolbarEditText.show()
        recyclerView = view.findViewById(R.id.fragment_search_recycler_view)
        recyclerView.adapter = searchImagesAdapter
    }

    private fun initListeners(){
        toolbarEditText.setOnClickListener {
            searchViewModel.onStart()
            recyclerView.adapter = queryAdapter
        }

        toolbarEditText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onSearch(query = toolbarEditText.text.toString())

                return@OnKeyListener true
            }

            false
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    searchViewModel.getMoreImages(query = toolbarEditText.text.toString())
                }
            }
        })
    }

    private fun onSearch(query: String){
        searchViewModel.onSearchImage(query = query)
        recyclerView.adapter = searchImagesAdapter
    }

    private fun buildToolbar() {
        toolbar = ToolbarBuilder()
            .setMenu(R.menu.search_toolbar_menu)
            .setNavigationIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back))
            .build(activity = APP_ACTIVITY)

        toolbar.setOnMenuItemClickListener {
            toolbarEditText.text.clear()
            recyclerView.adapter = searchImagesAdapter

            true
        }

        toolbar.setNavigationOnClickListener {
            navigation.back()
            toolbarEditText.hide()
        }
    }

    private fun observeLiveData() {
        searchViewModel.queryHistoryLiveData.observe(viewLifecycleOwner, ::handleSearchHistory)
        searchViewModel.imagesLiveData.observe(viewLifecycleOwner, ::handleImages)
        searchViewModel.moreImagesLiveData.observe(viewLifecycleOwner, ::handleMoreImages)
        searchViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleSearchHistory(list: List<QueryHistory>) {
        queryAdapter.setList(list)
    }

    private fun handleImages(responseData: ResponseData<List<SearchItem>, String>) {
        when (responseData) {
            is ResponseData.Success -> showImages(responseData.result)
            is ResponseData.Error -> showError()
        }
    }

    private fun showImages(list: List<SearchItem>){
        searchImagesAdapter.setList(list)
        loadingError.hide()
    }

    private fun showError(){
        loadingError.show()
        loadingError.onRetryClick {
            searchViewModel.onSearchImage(query = toolbarEditText.text.toString())
        }
    }

    private fun handleMoreImages(responseData: ResponseData<List<SearchItem>, String>) {
        when (responseData) {
            is ResponseData.Success -> {
                searchImagesAdapter.hideLoadingItem()
                searchImagesAdapter.setMoreList(responseData.result)
            }
            is ResponseData.Error -> searchImagesAdapter.changeMoreLoadingItem(
                isLoading = false,
                isError = true
            )
        }
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}