package com.aslnstbk.unsplash.favorite_images.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.favorite_images.domain.FavoriteImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteImagesViewModel(
    private val favoriteImageRepository: FavoriteImageRepository
) : ViewModel() {

    val favoriteImagesLiveData: LiveData<List<FavoriteImage>> = getAllFavoriteImages()

    private fun getAllFavoriteImages(): LiveData<List<FavoriteImage>> {
        var liveData: LiveData<List<FavoriteImage>> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {
            liveData = favoriteImageRepository.getAllFavoriteImages()
        }

        return liveData
    }
}