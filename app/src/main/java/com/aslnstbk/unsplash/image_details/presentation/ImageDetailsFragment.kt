package com.aslnstbk.unsplash.image_details.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.view.LoadingError
import com.aslnstbk.unsplash.common.presentation.view.ToolbarBuilder
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val IMAGE_ID_BUNDLE_KEY = "image_id_key"
const val EMAIL_TEXT_FORMAT = "@%s"
const val DEFAULT_IMAGE_ID = "RlTHiLHE7Pg"

class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val imageDetailsViewModel: ImageDetailsViewModel by viewModel()
    private val navigation: Navigation by inject()
    private val imageLoader: ImageLoader by inject()
    private val loadingError: LoadingError by inject()

    private lateinit var toolbar: Toolbar
    private lateinit var ownerPhotoImageView: ImageView
    private lateinit var ownerFullNameTextView: TextView
    private lateinit var ownerEmailTextView: TextView
    private lateinit var imageImageView: ImageView
    private lateinit var favoriteButton: ImageView
    private lateinit var downloadButton: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageDetailsViewModel.onStart(getImageIdFromBundle())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        buildToolbar()
        observeLiveData()
        view.hide()
    }

    private fun getImageIdFromBundle(): String {
        val bundle: Bundle? = arguments

        return bundle?.getString(IMAGE_ID_BUNDLE_KEY, DEFAULT_IMAGE_ID) ?: DEFAULT_IMAGE_ID
    }

    private fun initViews(view: View) {
        ownerPhotoImageView = view.findViewById(R.id.fragment_image_details_image_owner_photo)
        ownerFullNameTextView = view.findViewById(R.id.fragment_image_details_image_owner_full_name)
        ownerEmailTextView = view.findViewById(R.id.fragment_image_details_image_owner_email)
        imageImageView = view.findViewById(R.id.fragment_image_details_image)
        favoriteButton = view.findViewById(R.id.fragment_image_details_button_favorite)
        downloadButton = view.findViewById(R.id.fragment_image_details_button_download)
        progressBar = APP_ACTIVITY.findViewById(R.id.activity_main_progress_bar)
    }

    private fun buildToolbar() {
        toolbar = ToolbarBuilder()
            .setNavigationIcon(ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_arrow_back
            ))
            .build(activity = APP_ACTIVITY)

        toolbar.setNavigationOnClickListener {
            navigation.back()
        }
    }

    private fun observeLiveData() {
        imageDetailsViewModel.imageLiveData.observe(viewLifecycleOwner, ::handleImage)
        imageDetailsViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleImage(responseData: ResponseData<Image, String>) {
        when (responseData) {
            is ResponseData.Success -> fillData(responseData.result)
            is ResponseData.Error -> showLoadingError()
        }
    }

    private fun fillData(image: Image) {
        ownerFullNameTextView.text = image.user.name
        ownerEmailTextView.text = EMAIL_TEXT_FORMAT.format(image.user.username)

        loadImages(image = image)
        setFavoriteImage(isFavorite = image.isFavorite)
        onFavoriteButtonClick(image = image)
        onDownloadButtonClick(image = image)

        loadingError.hide()
        view?.show()
    }

    private fun loadImages(image: Image) {
        imageLoader.load(
            url = image.user.profile_photo.small,
            target = ownerPhotoImageView,
            withCenterCrop = false
        )
        imageLoader.load(
            url = image.urls.regular,
            target = imageImageView,
            withCenterCrop = false
        )
    }

    private fun setFavoriteImage(isFavorite: Boolean) = if (isFavorite) {
        favoriteButton.setBackgroundResource(R.drawable.ic_favorite_bold_red)
    } else {
        favoriteButton.setBackgroundResource(R.drawable.ic_favorite_border)
    }

    private fun onFavoriteButtonClick(image: Image) {
        favoriteButton.setOnClickListener {
            imageDetailsViewModel.onFavoriteButtonClick(image = image)
        }
    }

    private fun onDownloadButtonClick(image: Image) {
        downloadButton.setOnClickListener {
            imageDetailsViewModel.onDownloadImage(image = image)
        }
    }

    private fun showLoadingError() {
        loadingError.show()
        loadingError.onRetryClick {
            imageDetailsViewModel.onStart(getImageIdFromBundle())
        }
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}