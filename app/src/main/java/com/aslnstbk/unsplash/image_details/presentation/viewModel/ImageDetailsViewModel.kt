package com.aslnstbk.unsplash.image_details.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.history.data.models.History
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageDetailsViewModel(
    private val imageDetailsRepository: ImageDetailsRepository
) : ViewModel() {

    val imageLiveData: MutableLiveData<ResponseData<Image, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart(imageId: String) {
        progressLiveData.value = ProgressState.Loading
        getFavoriteImages(imageId)
    }

    fun addFavoriteImage(image: Image) = CoroutineScope(Dispatchers.IO).launch {
        imageDetailsRepository.addFavoriteImage(
            FavoriteImage(
                imageId = image.id,
                imageUrl = image.urls.regular
            )
        )
        getFavoriteImages(image.id)
    }

    fun removeFavoriteImage(image: Image) = CoroutineScope(Dispatchers.IO).launch {
        imageDetailsRepository.removeFavoriteImage(imageId = image.id)
        getFavoriteImages(image.id)
    }

    fun addToHistory(image: Image) = CoroutineScope(Dispatchers.IO).launch {
        imageDetailsRepository.addToHistory(
            History(
                imageId = image.id,
                imageUrl = image.urls.regular
            )
        )
    }

    private fun getFavoriteImages(imageId: String) {
        imageDetailsRepository.getImageById(
            photoId = imageId,
            result = {
                setFavoriteImage(it)

                imageLiveData.value = ResponseData.Success(it)
                progressLiveData.value = ProgressState.Done
            },
            fail = {
                imageLiveData.value = ResponseData.Error(it.toString())
                progressLiveData.value = ProgressState.Done
            }
        )
    }

    private fun setFavoriteImage(image: Image) = CoroutineScope(Dispatchers.IO).launch {
        val isFavorite: Boolean = imageDetailsRepository.checkById(imageId = image.id)

        if (isFavorite) {
            image.isFavorite = !image.isFavorite
        }
    }
}