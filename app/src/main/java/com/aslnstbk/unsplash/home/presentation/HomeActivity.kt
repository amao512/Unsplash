package com.aslnstbk.unsplash.home.presentation

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.view.PhotosAdapter
import com.aslnstbk.unsplash.home.presentation.viewmodel.HomeViewModel
import com.aslnstbk.unsplash.utils.hide
import com.aslnstbk.unsplash.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var failTextView: TextView
    private lateinit var progressBar: ProgressBar

    private val photosAdapter: PhotosAdapter by lazy {
        PhotosAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel.onStart()

        initViews()
        observeLiveData()
    }

    private fun initViews() {
        failTextView = findViewById(R.id.activity_home_fail)
        progressBar = findViewById(R.id.activity_home_progress_bar)
        recyclerView = findViewById(R.id.activity_home_recycler_view)
        recyclerView.adapter = photosAdapter
    }

    private fun observeLiveData() {
        homeViewModel.liveDataItem.observe(this, ::handlePhotos)
        homeViewModel.progressLiveData.observe(this, ::handleProgress)
    }

    private fun handlePhotos(responseData: ResponseData<List<HomeListItem>, String>) {
        when(responseData){
            is ResponseData.Success -> {
                photosAdapter.setPhotoList(responseData.result)
            }
            is ResponseData.Error -> {
                failTextView.text = responseData.error
            }
        }
    }

    private fun handleProgress(progressState: ProgressState) {
        when(progressState){
            ProgressState.Loading -> progressBar.show()
            ProgressState.Done -> progressBar.hide()
        }
    }
}