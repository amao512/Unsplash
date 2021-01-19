package com.aslnstbk.unsplash.image_details.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.models.Photo
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val IMAGE_ID_BUNDLE_KEY = "image_id_key"
const val EMAIL_TEXT_FORMAT = "@%s"

class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val imageDetailsViewModel: ImageDetailsViewModel by viewModel()

    private val imageLoader: ImageLoader by inject()

    private lateinit var ownerPhotoImageView: ImageView
    private lateinit var ownerFullNameTextView: TextView
    private lateinit var ownerEmailTextView: TextView
    private lateinit var imageImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imageDetailsViewModel.onStart(getImageIdFromBundle())

        initViews(view)
        observeLiveData()
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
    }

    private fun observeLiveData() {
        imageDetailsViewModel.imageLiveData.observe(viewLifecycleOwner, ::handleImage)
    }

    private fun handleImage(responseData: ResponseData<Photo, String>) {
        when(responseData){
            is ResponseData.Success -> fillData(responseData.result)
            is ResponseData.Error -> {}
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
    }
}