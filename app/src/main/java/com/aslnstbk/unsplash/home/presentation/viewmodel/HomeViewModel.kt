package com.aslnstbk.unsplash.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.images_line.ImageItem

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel(){

    val imagesLiveData: MutableLiveData<ResponseData<List<ImageItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart(){
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

    fun onSearchImage(query: String){
        progressLiveData.value = ProgressState.Loading

        homeRepository.searchImages(
            query = query,
            result = {
                imagesLiveData.value = ResponseData.Success(getImageItemsList(it.results))
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