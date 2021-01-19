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
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.data.models.Photo
import com.aslnstbk.unsplash.common.view.ToolbarBuilder
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.utils.hide
import com.aslnstbk.unsplash.utils.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val IMAGE_ID_BUNDLE_KEY = "image_id_key"
const val EMAIL_TEXT_FORMAT = "@%s"

class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val imageDetailsViewModel: ImageDetailsViewModel by viewModel()
    private val navigation: Navigation by inject()

    private val imageLoader: ImageLoader by inject()

    private lateinit var toolbar: Toolbar
    private lateinit var ownerPhotoImageView: ImageView
    private lateinit var ownerFullNameTextView: TextView
    private lateinit var ownerEmailTextView: TextView
    private lateinit var imageImageView: ImageView
    private lateinit var failTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imageDetailsViewModel.onStart(getImageIdFromBundle())

        initViews(view)
        buildToolbar()
        observeLiveData()
        view.hide()
    }

    private fun getImageIdFromBundle(): String {
        val bundle: Bundle? = arguments

        return bundle?.getString(IMAGE_ID_BUNDLE_KEY, "RlTHiLHE7Pg") ?: "RlTHiLHE7Pg"
    }

    private fun initViews(view: View) {
        ownerPhotoImageView = view.findViewById(R.id.fragment_image_details_image_owner_photo)
        ownerFullNameTextView = view.findViewById(R.id.fragment_image_details_image_owner_full_name)
        ownerEmailTextView = view.findViewById(R.id.fragment_image_details_image_owner_email)
        imageImageView = view.findViewById(R.id.fragment_image_details_image)
        failTextView = APP_ACTIVITY.findViewById(R.id.activity_main_fail)
        progressBar = APP_ACTIVITY.findViewById(R.id.activity_main_progress_bar)
    }

    private fun buildToolbar(){
        toolbar = ToolbarBuilder()
            .setNavigationIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back))
            .build(activity = APP_ACTIVITY)

        toolbar.setNavigationOnClickListener {
            navigation.back()
        }
    }

    private fun observeLiveData() {
        imageDetailsViewModel.imageLiveData.observe(viewLifecycleOwner, ::handleImage)
        imageDetailsViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleImage(responseData: ResponseData<Photo, String>) {
        when(responseData){
            is ResponseData.Success -> fillData(responseData.result)
            is ResponseData.Error -> failTextView.show()
        }
    }

    private fun fillData(photo: Photo){
        ownerFullNameTextView.text = photo.user.name
        ownerEmailTextView.text = EMAIL_TEXT_FORMAT.format(photo.user.username)

        imageLoader.loadImage(
            url = photo.user.profile_image.small,
            target = ownerPhotoImageView
        )
        imageLoader.loadImage(
            url = photo.urls.regular,
            target = imageImageView
        )

        view?.show()
    }

    private fun handleProgress(progressState: ProgressState) {
        when(progressState){
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}