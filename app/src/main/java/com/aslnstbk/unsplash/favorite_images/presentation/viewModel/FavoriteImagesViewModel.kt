package com.aslnstbk.unsplash.favorite_images.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.favorite_images.domain.FavoriteImageRepository
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoriteImagesViewModel(
    private val mainJob: Job = Job(),
    override val coroutineContext: CoroutineContext = mainJob + Dispatchers.Main,
    private val ioContext: CoroutineContext = mainJob + Dispatchers.IO,
    private val favoriteImageRepository: FavoriteImageRepository
) : ViewModel(), CoroutineScope {

    val favoriteImagesLiveData: MutableLiveData<List<ImageItem>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        mainJob.cancel()
    }

    fun onStart() = launch(coroutineContext) {
        progressLiveData.value = ProgressState.Loading
        getAllFavoriteImages()
    }

    private fun getAllFavoriteImages() = launch(ioContext) {
        favoriteImagesLiveData.postValue(prepareImagesList(favoriteImageRepository.getAllFavoriteImages().reversed()))
        progressLiveData.postValue(ProgressState.Done)
    }

    private fun prepareImagesList(list: List<FavoriteImage>): List<ImageItem> {
        return list.map {
            ImageItem(
                imageId = it.imageId,
                imageUrl = it.imageUrl
            )
        }
    }
}