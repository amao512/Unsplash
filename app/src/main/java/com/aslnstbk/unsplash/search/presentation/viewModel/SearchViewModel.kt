package com.aslnstbk.unsplash.search.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.search.data.models.SearchHistory
import com.aslnstbk.unsplash.search.domain.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    val searchHistoryLiveData: MutableLiveData<List<SearchHistory>> = MutableLiveData()
    val imagesLiveData: MutableLiveData<ResponseData<List<ImageItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart() {
        getAllSearchHistory()
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) = CoroutineScope(Dispatchers.IO).launch {
        searchRepository.deleteSearchHistory(searchHistory)
    }

    fun onSearchImage(query: String) {
        progressLiveData.value = ProgressState.Loading

        searchRepository.searchImages(
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

        addSearchHistory(query = query)
    }

    private fun getAllSearchHistory() = CoroutineScope(Dispatchers.IO).launch {
        searchHistoryLiveData.postValue(searchRepository.getAllSearchHistory().reversed())
    }

    private fun addSearchHistory(query: String) = CoroutineScope(Dispatchers.IO).launch {
        if (!searchRepository.checkByQuery(query)) {
            searchRepository.addSearchHistory(
                SearchHistory(query = query)
            )
        }
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