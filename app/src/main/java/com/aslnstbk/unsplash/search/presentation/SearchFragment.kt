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
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.common.presentation.view.ImagesLineAdapter
import com.aslnstbk.unsplash.common.presentation.view.LoadingError
import com.aslnstbk.unsplash.common.presentation.view.ToolbarBuilder
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.image_details.presentation.IMAGE_ID_BUNDLE_KEY
import com.aslnstbk.unsplash.image_details.presentation.ImageDetailsFragment
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.main.MainRouter
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.search.data.models.SearchHistory
import com.aslnstbk.unsplash.search.presentation.view.SearchAdapter
import com.aslnstbk.unsplash.search.presentation.view.SearchListener
import com.aslnstbk.unsplash.search.presentation.viewModel.SearchViewModel
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search), SearchListener, ImageClickListener {

    private val searchViewModel: SearchViewModel by viewModel()
    private val mainRouter: MainRouter by inject()
    private val navigation: Navigation by inject()
    private val imageLoader: ImageLoader by inject()
    private val loadingError: LoadingError by inject()

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(searchListener = this)
    }

    private val imagesLineAdapter: ImagesLineAdapter by lazy {
        ImagesLineAdapter(
            imageClickListener = this,
            imageLoader = imageLoader
        )
    }

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarEditText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerProgressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildToolbar()
        initViews(view)
        initListeners()
        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        toolbarEditText.hide()
    }

    override fun onSearchHistoryDelete(
        searchHistory: SearchHistory,
        position: Int
    ) {
        searchViewModel.deleteSearchHistory(searchHistory)
        searchAdapter.removeFromList(
            searchHistory = searchHistory,
            position = position
        )
    }

    override fun onSearchHistoryClick(query: String) {
        toolbarEditText.setText(query)
        onSearch(query = query)
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

    private fun initViews(view: View) {
        progressBar = APP_ACTIVITY.findViewById(R.id.activity_main_progress_bar)
        toolbarEditText = APP_ACTIVITY.findViewById(R.id.activity_main_toolbar_edit_text)
        toolbarEditText.show()
        recyclerView = view.findViewById(R.id.fragment_search_recycler_view)
        recyclerView.adapter = imagesLineAdapter
        recyclerProgressBar = view.findViewById(R.id.fragment_search_progress_bar)
    }

    private fun initListeners(){
        toolbarEditText.setOnClickListener {
            searchViewModel.onStart()
            recyclerView.adapter = searchAdapter
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
                } else {
                    recyclerProgressBar.hide()
                }
            }
        })
    }

    private fun onSearch(query: String){
        searchViewModel.onSearchImage(query = query)
        recyclerView.adapter = imagesLineAdapter
    }

    private fun buildToolbar() {
        toolbar = ToolbarBuilder()
            .setMenu(R.menu.search_toolbar_menu)
            .setNavigationIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back))
            .build(activity = APP_ACTIVITY)

        toolbar.setOnMenuItemClickListener {
            toolbarEditText.text.clear()
            recyclerView.adapter = imagesLineAdapter

            true
        }

        toolbar.setNavigationOnClickListener {
            navigation.back()
            toolbarEditText.hide()
        }
    }

    private fun observeLiveData() {
        searchViewModel.searchHistoryLiveData.observe(viewLifecycleOwner, ::handleSearchHistory)
        searchViewModel.imagesLiveData.observe(viewLifecycleOwner, ::handleImages)
        searchViewModel.moreImagesLiveData.observe(viewLifecycleOwner, ::handleMoreImages)
        searchViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
        searchViewModel.moreProgressLiveData.observe(viewLifecycleOwner, ::handleMoreProgress)
    }

    private fun handleSearchHistory(list: List<SearchHistory>) {
        searchAdapter.setList(list)
    }

    private fun handleImages(responseData: ResponseData<List<ImageItem>, String>) {
        when (responseData) {
            is ResponseData.Success -> showImages(responseData.result)
            is ResponseData.Error -> showError()
        }
    }

    private fun handleMoreImages(responseData: ResponseData<List<ImageItem>, String>) {
        when (responseData) {
            is ResponseData.Success -> imagesLineAdapter.setMoreList(responseData.result)
            is ResponseData.Error -> showError()
        }
    }

    private fun showImages(list: List<ImageItem>){
        imagesLineAdapter.setList(list)
        loadingError.hide()
    }

    private fun showError(){
        loadingError.show()
        loadingError.onRetryClick {
            searchViewModel.onSearchImage(query = toolbarEditText.text.toString())
        }
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }

    private fun handleMoreProgress(progressState: ProgressState?) {
        when (progressState) {
            is ProgressState.Loading -> recyclerProgressBar.show()
            is ProgressState.Done -> recyclerProgressBar.hide()
        }
    }
}