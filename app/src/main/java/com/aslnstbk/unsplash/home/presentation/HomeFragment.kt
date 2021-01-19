package com.aslnstbk.unsplash.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.view.PhotosAdapter
import com.aslnstbk.unsplash.home.presentation.viewmodel.HomeViewModel
import com.aslnstbk.unsplash.main.MainActivity
import com.aslnstbk.unsplash.utils.hide
import com.aslnstbk.unsplash.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var failTextView: TextView
    private lateinit var progressBar: ProgressBar

    private val photosAdapter: PhotosAdapter by lazy {
        PhotosAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.onStart()

        initViews(view)
        observeLiveData()
    }

    private fun initViews(view: View) {
        failTextView = (activity as MainActivity).findViewById(R.id.activity_main_fail)
        progressBar = (activity as MainActivity).findViewById(R.id.activity_main_progress_bar)
        recyclerView = view.findViewById(R.id.fragment_home_recycler_view)
        recyclerView.adapter = photosAdapter
    }

    private fun observeLiveData() {
        homeViewModel.liveDataItem.observe(viewLifecycleOwner, ::handlePhotos)
        homeViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handlePhotos(responseData: ResponseData<List<HomeListItem>, String>) {
        when(responseData){
            is ResponseData.Success -> {
                photosAdapter.setPhotoList(responseData.result)
                failTextView.hide()
            }
            is ResponseData.Error -> {
                failTextView.show()
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