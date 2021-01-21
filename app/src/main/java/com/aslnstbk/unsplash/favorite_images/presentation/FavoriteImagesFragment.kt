package com.aslnstbk.unsplash.favorite_images.presentation

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
import com.aslnstbk.unsplash.common.presentation.view.ToolbarBuilder
import com.aslnstbk.unsplash.favorite_images.presentation.viewModel.FavoriteImagesViewModel
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.image_details.presentation.IMAGE_ID_BUNDLE_KEY
import com.aslnstbk.unsplash.image_details.presentation.ImageDetailsFragment
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.common.presentation.view.ImagesLineAdapter
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.main.MainRouter
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteImagesFragment : Fragment(R.layout.fragment_favorite_images), ImageClickListener {

    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val navigation: Navigation by inject()
    private val mainRouter: MainRouter by inject()

    private val imagesLineAdapter: ImagesLineAdapter by lazy {
        ImagesLineAdapter(
            imageLoader = imageLoader,
            imageClickListener = this,
        )
    }

    private lateinit var toolbar: Toolbar
    private lateinit var favoriteFab: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteImagesViewModel.onStart()

        initViews(view)
        buildToolbar()
        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        favoriteFab.show()
    }

    override fun onImageClick(imageId: String) {
        favoriteFab.show()
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
        favoriteFab = APP_ACTIVITY.findViewById(R.id.activity_main_fab_favorite)
        favoriteFab.hide()
        emptyTextView = view.findViewById(R.id.fragment_favorite_images_text_view_empty)
        recyclerView = view.findViewById(R.id.fragment_favorite_images_recycler_view)
        recyclerView.adapter = imagesLineAdapter
    }

    private fun buildToolbar() {
        toolbar = ToolbarBuilder()
            .setNavigationIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back))
            .build(activity = APP_ACTIVITY)

        toolbar.setNavigationOnClickListener {
            navigation.back()
        }
    }

    private fun observeLiveData() {
        favoriteImagesViewModel.favoriteImagesLiveData.observe(viewLifecycleOwner, ::handleFavoriteImages)
        favoriteImagesViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleFavoriteImages(list: List<ImageItem>) {
        if (list.isEmpty()) {
            emptyTextView.show()
            recyclerView.hide()
        } else {
            imagesLineAdapter.setList(list)
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