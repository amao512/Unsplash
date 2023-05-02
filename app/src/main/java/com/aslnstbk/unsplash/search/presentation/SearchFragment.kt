package com.aslnstbk.unsplash.search.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.constants.ArgConstants
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.databinding.FragmentSearchBinding
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.search.data.models.QueryHistory
import com.aslnstbk.unsplash.search.presentation.models.SearchItem
import com.aslnstbk.unsplash.search.presentation.view.SearchListener
import com.aslnstbk.unsplash.search.presentation.view.query.QueryAdapter
import com.aslnstbk.unsplash.search.presentation.view.search_image.SearchImagesAdapter
import com.aslnstbk.unsplash.search.presentation.viewModel.SearchViewModel
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search), SearchListener, ImageClickListener {

    private val binding: FragmentSearchBinding by viewBinding()

    private val viewModel: SearchViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
        observeViewModel()
        viewModel.onViewCreated(arguments)
    }

    override fun onQueryHistoryDelete(
        queryHistory: QueryHistory,
        position: Int
    ) {
        viewModel.deleteSearchHistory(queryHistory)
        queryAdapter.removeFromList(
            queryHistory = queryHistory,
            position = position
        )
    }

    override fun onQueryHistoryClick(query: String) {
        binding.searchEditText.setText(query)
        onSearch(query = query)
    }

    override fun onMoreRetryClick() {
        searchImagesAdapter.changeMoreLoadingItem(
            isLoading = true,
            isError = false
        )
        viewModel.fetchMoreImages()
    }

    override fun onImageClick(imageId: String) {
        findNavController().navigate(R.id.action_searchFragment_to_imageDetailsFragment, bundleOf(
            ArgConstants.ARG_IMAGE_ID to imageId
        ))
    }

    private fun setupViews() = with(binding) {
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        recyclerView.adapter = queryAdapter
    }

    private fun setupListeners() = with(binding) {
        searchEditText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onSearch(query = searchEditText.text.toString())

                return@OnKeyListener true
            }

            false
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.fetchMoreImages()
                }
            }
        })
    }

    private fun onSearch(query: String) = with(binding) {
        viewModel.onSearchImage(query = query)
        recyclerView.adapter = searchImagesAdapter
    }

    private fun observeViewModel() = with(viewModel) {
        query.observe(viewLifecycleOwner) {
            binding.searchEditText.setText(it)
        }
        queryHistoryLiveData.observe(viewLifecycleOwner, ::handleSearchHistory)
        imagesLiveData.observe(viewLifecycleOwner, ::handleImages)
        moreImagesLiveData.observe(viewLifecycleOwner, ::handleMoreImages)
        progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
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
        binding.errorLayout.root.hide()
    }

    private fun showError() = with(binding) {
        errorLayout.root.show()
        errorLayout.layoutLoadingErrorRetryButton.setOnClickListener {
            errorLayout.root.hide()
            viewModel.onSearchImage(query = searchEditText.text.toString())
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

    private fun handleProgress(progressState: ProgressState) = with(binding) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}