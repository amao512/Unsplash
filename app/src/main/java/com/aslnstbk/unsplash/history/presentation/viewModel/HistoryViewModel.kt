package com.aslnstbk.unsplash.history.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.history.data.models.History
import com.aslnstbk.unsplash.history.domain.HistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    val historyImagesLiveData: MutableLiveData<List<History>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun onStart(){
        progressLiveData.value = ProgressState.Loading
        getAllHistory()
    }

    private fun getAllHistory() = CoroutineScope(Dispatchers.IO).launch {
        historyImagesLiveData.postValue(historyRepository.getAllHistory())
        progressLiveData.postValue(ProgressState.Done)
    }
}