package com.aslnstbk.unsplash.image_details.presentation

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.constants.ArgConstants.ARG_QUERY
import com.aslnstbk.unsplash.common.data.ImageViewer
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.databinding.FragmentImageDetailsBinding
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.apmem.tools.layouts.FlowLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EMAIL_TEXT_FORMAT = "@%s"

class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val binding: FragmentImageDetailsBinding by viewBinding()

    private val viewModel: ImageDetailsViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val imageViewer: ImageViewer by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
        viewModel.onViewCreated(arguments)
    }

    private fun setupViews() = with(binding) {
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        favouritesButton.setOnClickListener {
            findNavController().navigate(R.id.action_imageDetailsFragment_to_favoriteImagesFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.imageLiveData.observe(viewLifecycleOwner, ::handleImage)
        viewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleImage(responseData: ResponseData<Image, String>) {
        when (responseData) {
            is ResponseData.Success -> fillData(responseData.result)
            is ResponseData.Error -> showLoadingError()
        }
    }

    private fun fillData(image: Image) = with(binding) {
        fragmentImageDetailsImageOwnerFullName.text = image.user.name
        fragmentImageDetailsImageOwnerEmail.text = EMAIL_TEXT_FORMAT.format(image.user.username)

        loadImages(image = image)
        setFavoriteImage(isFavorite = image.isFavorite)
        onFavoriteButtonClick(image = image)
        onDownloadButtonClick(image = image)
        fillTags(image = image)

        errorLayout.root.hide()
    }

    private fun loadImages(image: Image) = with(binding) {
        imageLoader.load(
            url = image.user.profile_photo.small,
            target = fragmentImageDetailsImageOwnerPhoto,
            withCenterCrop = false
        )
        imageLoader.load(
            url = image.urls.regular,
            target = fragmentImageDetailsImage,
            withCenterCrop = false
        )

        fragmentImageDetailsImage.setOnClickListener {
            imageViewer.show(
                context = requireContext(),
                images = listOf(image.urls.regular)
            )
        }
    }

    private fun setFavoriteImage(isFavorite: Boolean) = with(binding) {
        val resource = if (isFavorite) {
            R.drawable.ic_favorite_bold_red
        } else {
            R.drawable.ic_favorite_border
        }

        fragmentImageDetailsButtonFavorite.setBackgroundResource(resource)
    }

    private fun onFavoriteButtonClick(image: Image) {
        binding.fragmentImageDetailsButtonFavorite.setOnClickListener {
            viewModel.onFavoriteButtonClick(image = image)
        }
    }

    private fun onDownloadButtonClick(image: Image) {
        binding.fragmentImageDetailsButtonDownload.setOnClickListener {
            viewModel.onDownloadImage(image = image)
        }
    }

    private fun fillTags(image: Image) {
        binding.fragmentImageDetailsTags.removeAllViews()

        image.tags.map { tag ->
            val tagTextView = createTagTextView(tag.title)
            tagTextView.setOnClickListener {
                searchByTag(tag.title)
            }

            binding.fragmentImageDetailsTags.addView(tagTextView)
        }
    }

    private fun searchByTag(title: String) {
        findNavController().navigate(R.id.action_imageDetailsFragment_to_searchFragment, bundleOf(
            ARG_QUERY to title
        ))
    }

    private fun createTagTextView(tag: String): TextView {
        val params = FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 10, 10)
        val tagTextView = TextView(requireContext()).apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.colorTagText))
            setBackgroundResource(R.drawable.bg_tag)
            layoutParams = params
            textSize = 18f
            text = tag
        }

        return tagTextView
    }

    private fun showLoadingError() = with(binding) {
        errorLayout.root.show()
        errorLayout.layoutLoadingErrorRetryButton.setOnClickListener {
            errorLayout.root.hide()
            viewModel.onViewCreated(arguments)
        }
    }

    private fun handleProgress(progressState: ProgressState) = with(binding) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}