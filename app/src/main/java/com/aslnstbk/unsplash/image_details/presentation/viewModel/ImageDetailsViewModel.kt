package com.aslnstbk.unsplash.image_details.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Photo
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository

class ImageDetailsViewModel(
    private val imageDetailsRepository: ImageDetailsRepository
) : ViewModel() {

    val imageLiveData: MutableLiveData<ResponseData<Photo, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart(photoId: String){
        progressLiveData.value = ProgressState.Loading

        imageDetailsRepository.getPhotoById(
            photoId = photoId,
            result = {
                imageLiveData.value = ResponseData.Success(it)
                progressLiveData.value = ProgressState.Done
            },
            fail = {
                imageLiveData.value = ResponseData.Error(it.toString())
                progressLiveData.value = ProgressState.Done
            }
        )
    }
}