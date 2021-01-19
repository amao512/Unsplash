package com.aslnstbk.unsplash.image_details.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Photo
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository

class ImageDetailsViewModel(
    private val imageDetailsRepository: ImageDetailsRepository
) : ViewModel() {
    val imageLiveData: MutableLiveData<ResponseData<Photo, String>> = MutableLiveData()

    fun onStart(photoId: String){
        imageDetailsRepository.getPhotoById(
            photoId = photoId,
            result = {
                imageLiveData.value = ResponseData.Success(it)
            },
            fail = {
                imageLiveData.value = ResponseData.Error(it.toString())
            }
        )
    }
}