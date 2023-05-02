package com.aslnstbk.unsplash.favorite_images.presentation

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.constants.ArgConstants.ARG_IMAGE_ID
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.common.presentation.view.ImagesLineAdapter
import com.aslnstbk.unsplash.databinding.FragmentFavoriteImagesBinding
import com.aslnstbk.unsplash.favorite_images.presentation.viewModel.FavoriteImagesViewModel
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteImagesFragment : Fragment(R.layout.fragment_favorite_images), ImageClickListener {

    private val binding: FragmentFavoriteImagesBinding by viewBinding()

    private val viewModel: FavoriteImagesViewModel by viewModel()
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
        observeViewModel()
        viewModel.onViewCreated()
    }

    override fun onImageClick(imageId: String) {
        findNavController().navigate(R.id.action_favoriteImagesFragment_to_imageDetailsFragment, bundleOf(
            ARG_IMAGE_ID to imageId
        ))
    }

    private fun setupViews() = with(binding) {
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        recyclerView.adapter = imagesLineAdapter
    }

    private fun observeViewModel() = with(viewModel) {
        favoriteImagesLiveData.observe(viewLifecycleOwner, ::handleFavoriteImages)
        progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleFavoriteImages(list: List<ImageItem>) = with(binding) {
        if (list.isEmpty()) {
            emptyTextView.show()
            recyclerView.hide()
        } else {
            imagesLineAdapter.setList(list)
            recyclerView.show()
            emptyTextView.hide()
        }
    }

    private fun handleProgress(progressState: ProgressState) = with(binding) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}