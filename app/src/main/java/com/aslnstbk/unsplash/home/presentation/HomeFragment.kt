package com.aslnstbk.unsplash.home.presentation

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.constants.ArgConstants.ARG_IMAGE_ID
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.common.presentation.view.ImagesLineAdapter
import com.aslnstbk.unsplash.databinding.FragmentHomeBinding
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.home.presentation.viewmodel.HomeViewModel
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home), ImageClickListener {

    private val binding: FragmentHomeBinding by viewBinding()

    private val viewModel: HomeViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()

    private val imagesLineAdapter: ImagesLineAdapter by lazy {
        ImagesLineAdapter(
            imageLoader = imageLoader,
            imageClickListener = this,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
        observeViewModel()
        viewModel.onViewCreated()
    }

    override fun onImageClick(imageId: String) {
        findNavController().navigate(R.id.action_homeFragment_to_imageDetailsFragment, bundleOf(
            ARG_IMAGE_ID to imageId
        ))
    }

    private fun setupViews() = with(binding) {
        homeRecyclerView.adapter = imagesLineAdapter
    }

    private fun setupListeners() = with(binding) {
        toolbar.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

            true
        }

        favouritesButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteImagesFragment)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        imagesLiveData.observe(viewLifecycleOwner, ::handleImages)
        progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleImages(responseData: ResponseData<List<ImageItem>, String>) {
        when (responseData) {
            is ResponseData.Success -> showImages(responseData.result)
            is ResponseData.Error -> showLoadingError()
        }
    }

    private fun showImages(list: List<ImageItem>) = with(binding) {
        imagesLineAdapter.setList(list)
        errorLayout.root.hide()
        homeRecyclerView.show()
    }

    private fun showLoadingError() = with(binding) {
        homeRecyclerView.hide()
        errorLayout.root.show()
        errorLayout.layoutLoadingErrorRetryButton.setOnClickListener {
            errorLayout.root.hide()
            viewModel.onViewCreated()
        }
    }

    private fun handleProgress(progressState: ProgressState) = with(binding) {
        when (progressState) {
            ProgressState.Loading -> progressBar.show()
            ProgressState.Done -> progressBar.hide()
        }
    }
}