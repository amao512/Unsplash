package com.aslnstbk.unsplash.history.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.view.ToolbarBuilder
import com.aslnstbk.unsplash.history.data.models.History
import com.aslnstbk.unsplash.history.presentation.view.HistoryAdapter
import com.aslnstbk.unsplash.history.presentation.viewModel.HistoryViewModel
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.image_details.presentation.IMAGE_ID_BUNDLE_KEY
import com.aslnstbk.unsplash.image_details.presentation.ImageDetailsFragment
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.main.MainRouter
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history), ImageClickListener {

    private val historyViewModel: HistoryViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val navigation: Navigation by inject()
    private val mainRouter: MainRouter by inject()

    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter(
            imageLoader = imageLoader,
            imageClickListener = this
        )
    }

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.onStart()

        initViews(view)
        buildToolbar()
        observeLiveData()
    }

    override fun onImageClick(imageId: String) {
        val imageDetailsFragment = ImageDetailsFragment()
        val args = Bundle()
        args.putString(IMAGE_ID_BUNDLE_KEY, imageId)
        imageDetailsFragment.arguments = args

        navigation.navigate(
            mainRouter.setScreen(
                fragment = imageDetailsFragment,
                isBackStack = true
            )
        )
    }

    private fun initViews(view: View) {
        progressBar = APP_ACTIVITY.findViewById(R.id.activity_main_progress_bar)
        emptyTextView = view.findViewById(R.id.fragment_history_text_view_empty)
        recyclerView = view.findViewById(R.id.fragment_history_recycler_view)
        recyclerView.adapter = historyAdapter
    }

    private fun buildToolbar() {
        toolbar = ToolbarBuilder()
            .setNavigationIcon(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_arrow_back
                )
            )
            .build(activity = APP_ACTIVITY)

        toolbar.setNavigationOnClickListener {
            navigation.back()
        }
    }

    private fun observeLiveData() {
        historyViewModel.historyImagesLiveData.observe(viewLifecycleOwner, ::handleFavoriteImages)
        historyViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleFavoriteImages(list: List<History>) {
        if (list.isEmpty()) {
            emptyTextView.show()
            recyclerView.hide()
        } else {
            historyAdapter.setList(list)
            recyclerView.show()
            emptyTextView.hide()
        }
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}