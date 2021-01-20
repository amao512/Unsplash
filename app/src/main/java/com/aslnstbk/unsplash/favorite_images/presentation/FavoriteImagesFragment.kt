package com.aslnstbk.unsplash.favorite_images.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.view.ToolbarBuilder
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.favorite_images.presentation.view.FavoriteImagesAdapter
import com.aslnstbk.unsplash.favorite_images.presentation.viewModel.FavoriteImagesViewModel
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.image_details.presentation.IMAGE_ID_BUNDLE_KEY
import com.aslnstbk.unsplash.image_details.presentation.ImageDetailsFragment
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.main.MainRouter
import com.aslnstbk.unsplash.navigation.Navigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteImagesFragment : Fragment(R.layout.fragment_favorite_images), ImageClickListener {

    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val navigation: Navigation by inject()
    private val mainRouter: MainRouter by inject()

    private val favoriteImagesAdapter: FavoriteImagesAdapter by lazy {
        FavoriteImagesAdapter(
            imageLoader = imageLoader,
            imageClickListener = this
        )
    }

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        buildToolbar()
        observeLiveData()
    }

    override fun onClick(imageId: String) {
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
        recyclerView = view.findViewById(R.id.fragment_favorite_images_recycler_view)
        recyclerView.adapter = favoriteImagesAdapter
    }

    private fun buildToolbar(){
        toolbar = ToolbarBuilder()
            .setNavigationIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back))
            .build(activity = APP_ACTIVITY)

        toolbar.setNavigationOnClickListener {
            navigation.back()
        }
    }

    private fun observeLiveData() {
        favoriteImagesViewModel.favoriteImagesLiveData.observe(
            viewLifecycleOwner,
            ::handleFavoriteImages
        )
    }

    private fun handleFavoriteImages(list: List<FavoriteImage>) {
        favoriteImagesAdapter.setList(list)
    }
}