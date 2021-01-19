package com.aslnstbk.unsplash.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.models.Photo
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.models.PhotoListItem
import com.aslnstbk.unsplash.home.presentation.models.SearchBarItem

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel(){

    val liveDataItem: MutableLiveData<ResponseData<List<HomeListItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart(){
        progressLiveData.value = ProgressState.Loading

        homeRepository.getPhotos(
            result = {
                liveDataItem.value = ResponseData.Success(preparePhotosLiveData(it))
                progressLiveData.value = ProgressState.Done
            },
            fail = {
                liveDataItem.value = ResponseData.Error(it.toString())
                progressLiveData.value = ProgressState.Done
            }
        )
    }

    private fun preparePhotosLiveData(photoList: List<Photo>): List<HomeListItem> {
        return listOf(getSearchBarItem()) + getPhotoListItems(photoList)
    }

    private fun getSearchBarItem(): HomeListItem {
        return SearchBarItem(data = 0)
    }

    private fun getPhotoListItems(photoList: List<Photo>): List<HomeListItem> {
        return photoList.map {
            PhotoListItem(data = it)
        }
    }
}