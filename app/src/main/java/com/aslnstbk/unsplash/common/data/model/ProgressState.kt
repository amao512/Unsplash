package com.aslnstbk.unsplash.common.data.model

sealed class ProgressState {
    object Loading : ProgressState()
    object Done : ProgressState()
}