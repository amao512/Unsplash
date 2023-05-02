package com.aslnstbk.unsplash.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val mainJob: Job = Job(),
    override val coroutineContext: CoroutineContext = mainJob + Dispatchers.Main,
    private val homeRepository: HomeRepository
) : ViewModel(), CoroutineScope {

    val imagesLiveData: MutableLiveData<ResponseData<List<ImageItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        mainJob.cancel()
    }

    fun onViewCreated() {
        fetchImages()
    }

    private fun fetchImages() = launch(coroutineContext) {
        progressLiveData.value = ProgressState.Loading

        homeRepository.getImages(
            result = {
                imagesLiveData.value = ResponseData.Success(getImageItemsList(it))
                progressLiveData.value = ProgressState.Done
            },
            fail = {
                imagesLiveData.value = ResponseData.Error(it.toString())
                progressLiveData.value = ProgressState.Done
            }
        )
    }

    private fun getImageItemsList(imageList: List<Image>): List<ImageItem> {
        return imageList.map {
            ImageItem(
                imageId = it.id,
                imageUrl = it.urls.regular
            )
        }
    }
}