package com.aslnstbk.unsplash.image_details.presentation.viewModel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.constants.ArgConstants.ARG_IMAGE_ID
import com.aslnstbk.unsplash.common.constants.Constants.EMPTY
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ImageDetailsViewModel(
    private val mainJob: Job = Job(),
    override val coroutineContext: CoroutineContext = mainJob + Dispatchers.Main,
    private val ioContext: CoroutineContext = mainJob + Dispatchers.IO,
    private val imageDetailsRepository: ImageDetailsRepository
) : ViewModel(), CoroutineScope {

    val imageLiveData: MutableLiveData<ResponseData<Image, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        mainJob.cancel()
    }

    fun onViewCreated(args: Bundle?) {
        args?.let {
            if (it.containsKey(ARG_IMAGE_ID)) {
                val imageId = it.getString(ARG_IMAGE_ID, EMPTY)
                progressLiveData.value = ProgressState.Loading
                getFavoriteImages(imageId)
            }
        }
    }

    fun onFavoriteButtonClick(image: Image) = launch(coroutineContext) {
        if (image.isFavorite) {
            removeFavoriteImage(image = image)
        } else {
            addFavoriteImage(image = image)
        }
    }

    private fun getFavoriteImages(imageId: String) = launch(coroutineContext) {
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

    private fun setFavoriteImage(image: Image) = launch(ioContext) {
        val isFavorite: Boolean = imageDetailsRepository.checkById(imageId = image.id)

        if (isFavorite) {
            image.isFavorite = !image.isFavorite
        }
    }

    private fun addFavoriteImage(image: Image) = launch(ioContext) {
        imageDetailsRepository.addFavoriteImage(
            FavoriteImage(
                imageId = image.id,
                imageUrl = image.urls.regular
            )
        )

        getFavoriteImages(image.id)
    }

    private fun removeFavoriteImage(image: Image) = launch(ioContext) {
        imageDetailsRepository.removeFavoriteImage(imageId = image.id)
        getFavoriteImages(image.id)
    }
}