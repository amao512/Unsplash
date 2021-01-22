package com.aslnstbk.unsplash.favorite_images.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.favorite_images.domain.FavoriteImageRepository
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteImagesViewModel(
    private val favoriteImageRepository: FavoriteImageRepository
) : ViewModel() {

    val favoriteImagesLiveData: MutableLiveData<List<ImageItem>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart() {
        progressLiveData.value = ProgressState.Loading
        getAllFavoriteImages()
    }

    private fun getAllFavoriteImages() = CoroutineScope(Dispatchers.IO).launch {
        favoriteImagesLiveData.postValue(
            prepareImagesList(favoriteImageRepository.getAllFavoriteImages().reversed())
        )
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