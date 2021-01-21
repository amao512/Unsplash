package com.aslnstbk.unsplash.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.view.LoadingError
import com.aslnstbk.unsplash.common.view.ToolbarBuilder
import com.aslnstbk.unsplash.favorite_images.presentation.FavoriteImagesFragment
import com.aslnstbk.unsplash.history.presentation.HistoryFragment
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.view.HomeAdapter
import com.aslnstbk.unsplash.home.presentation.viewmodel.HomeViewModel
import com.aslnstbk.unsplash.image_details.presentation.IMAGE_ID_BUNDLE_KEY
import com.aslnstbk.unsplash.image_details.presentation.ImageDetailsFragment
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.main.MainRouter
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home), ImageClickListener, SearchListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val navigation: Navigation by inject()
    private val mainRouter: MainRouter by inject()
    private val loadingError: LoadingError by inject()

    private lateinit var toolbar: Toolbar

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(
            imageLoader = imageLoader,
            imageClickListener = this,
            searchListener = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onSearch(query: String) {
        homeViewModel.onSearchImage(query = query)
    }

    private fun initViews(view: View) {
        progressBar = APP_ACTIVITY.findViewById(R.id.activity_main_progress_bar)
        recyclerView = view.findViewById(R.id.fragment_home_recycler_view)
        recyclerView.adapter = homeAdapter
    }

    private fun buildToolbar() {
        toolbar = ToolbarBuilder()
            .setNavigationIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_app_logo))
            .setMenu(R.menu.toolbar_menu)
            .build(activity = APP_ACTIVITY)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.toolbar_menu_favorites_item -> navigation.navigate(
                    mainRouter.setScreen(
                        fragment = FavoriteImagesFragment(),
                        isBackStack = true
                    )
                )
                R.id.toolbar_menu_history_item -> navigation.navigate(
                    mainRouter.setScreen(
                        fragment = HistoryFragment(),
                        isBackStack = true
                    )
                )
            }

            true
        }
    }

    private fun observeLiveData() {
        homeViewModel.imagesLiveData.observe(viewLifecycleOwner, ::handleImages)
        homeViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleImages(responseData: ResponseData<List<HomeListItem>, String>) {
        when (responseData) {
            is ResponseData.Success -> {
                homeAdapter.setPhotoList(responseData.result)
                loadingError.hide()
            }
            is ResponseData.Error -> {
                loadingError.show()
                loadingError.onRetryClick {
                    homeViewModel.onStart()
                }
            }
        }
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            ProgressState.Loading -> progressBar.show()
            ProgressState.Done -> progressBar.hide()
        }
    }
}