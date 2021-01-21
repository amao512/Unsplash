package com.aslnstbk.unsplash.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.models.ImageListItem
import com.aslnstbk.unsplash.home.presentation.models.SearchBarItem

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel(){

    val imagesLiveData: MutableLiveData<ResponseData<List<HomeListItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart(){
        progressLiveData.value = ProgressState.Loading

        homeRepository.getImages(
            result = {
                imagesLiveData.value = ResponseData.Success(prepareImagesLiveData(it))
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
                imagesLiveData.value = ResponseData.Success(prepareImagesLiveData(it.results))
                progressLiveData.value = ProgressState.Done
            },
            fail = {
                imagesLiveData.value = ResponseData.Error(it.toString())
                progressLiveData.value = ProgressState.Done
            }
        )
    }

    private fun prepareImagesLiveData(imageList: List<Image>): List<HomeListItem> {
        return listOf(getSearchBarItem()) + getImageListItems(imageList)
    }

    private fun getSearchBarItem(): HomeListItem {
        return SearchBarItem(data = 0)
    }

    private fun getImageListItems(imageList: List<Image>): List<HomeListItem> {
        return imageList.map {
            ImageListItem(
                imageId = it.id,
                imageUrl = it.urls.regular
            )
        }
    }
}