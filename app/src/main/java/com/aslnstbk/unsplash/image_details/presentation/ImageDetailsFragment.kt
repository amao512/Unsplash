package com.aslnstbk.unsplash.image_details.presentation

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.ImageViewer
import com.aslnstbk.unsplash.common.data.model.ProgressState
import com.aslnstbk.unsplash.common.data.model.ResponseData
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.view.LoadingError
import com.aslnstbk.unsplash.common.presentation.view.ToolbarBuilder
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.main.MainRouter
import com.aslnstbk.unsplash.navigation.Navigation
import com.aslnstbk.unsplash.search.presentation.SearchFragment
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import org.apmem.tools.layouts.FlowLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val IMAGE_ID_BUNDLE_KEY = "image_id_key"
const val EMAIL_TEXT_FORMAT = "@%s"
const val DEFAULT_IMAGE_ID = "RlTHiLHE7Pg"
const val TAG_BUNDLE_KEY = "tag_bundle"

class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val imageDetailsViewModel: ImageDetailsViewModel by viewModel()
    private val navigation: Navigation by inject()
    private val mainRouter: MainRouter by inject()
    private val imageLoader: ImageLoader by inject()
    private val imageViewer: ImageViewer by inject()
    private val loadingError: LoadingError by inject()

    private lateinit var toolbar: Toolbar
    private lateinit var ownerPhotoImageView: ImageView
    private lateinit var ownerFullNameTextView: TextView
    private lateinit var ownerEmailTextView: TextView
    private lateinit var imageImageView: ImageView
    private lateinit var tagsLayout: FlowLayout
    private lateinit var favoriteButton: ImageView
    private lateinit var downloadButton: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageDetailsViewModel.onStart(getImageIdFromBundle())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        buildToolbar()
        observeLiveData()
        view.hide()
    }

    private fun getImageIdFromBundle(): String {
        val bundle: Bundle? = arguments

        return bundle?.getString(IMAGE_ID_BUNDLE_KEY, DEFAULT_IMAGE_ID) ?: DEFAULT_IMAGE_ID
    }

    private fun initViews(view: View) {
        ownerPhotoImageView = view.findViewById(R.id.fragment_image_details_image_owner_photo)
        ownerFullNameTextView = view.findViewById(R.id.fragment_image_details_image_owner_full_name)
        ownerEmailTextView = view.findViewById(R.id.fragment_image_details_image_owner_email)
        imageImageView = view.findViewById(R.id.fragment_image_details_image)
        tagsLayout = view.findViewById(R.id.fragment_image_details_tags)
        favoriteButton = view.findViewById(R.id.fragment_image_details_button_favorite)
        downloadButton = view.findViewById(R.id.fragment_image_details_button_download)
        progressBar = APP_ACTIVITY.findViewById(R.id.activity_main_progress_bar)
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
        imageDetailsViewModel.imageLiveData.observe(viewLifecycleOwner, ::handleImage)
        imageDetailsViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
    }

    private fun handleImage(responseData: ResponseData<Image, String>) {
        when (responseData) {
            is ResponseData.Success -> fillData(responseData.result)
            is ResponseData.Error -> showLoadingError()
        }
    }

    private fun fillData(image: Image) {
        ownerFullNameTextView.text = image.user.name
        ownerEmailTextView.text = EMAIL_TEXT_FORMAT.format(image.user.username)

        loadImages(image = image)
        setFavoriteImage(isFavorite = image.isFavorite)
        onFavoriteButtonClick(image = image)
        onDownloadButtonClick(image = image)
        fillTags(image = image)

        loadingError.hide()
        view?.show()
    }

    private fun loadImages(image: Image) {
        imageLoader.load(
            url = image.user.profile_photo.small,
            target = ownerPhotoImageView,
            withCenterCrop = false
        )
        imageLoader.load(
            url = image.urls.regular,
            target = imageImageView,
            withCenterCrop = false
        )

        imageImageView.setOnClickListener {
            imageViewer.show(
                context = requireContext(),
                images = listOf(image.urls.regular)
            )
        }
    }

    private fun setFavoriteImage(isFavorite: Boolean) = if (isFavorite) {
        favoriteButton.setBackgroundResource(R.drawable.ic_favorite_bold_red)
    } else {
        favoriteButton.setBackgroundResource(R.drawable.ic_favorite_border)
    }

    private fun onFavoriteButtonClick(image: Image) {
        favoriteButton.setOnClickListener {
            imageDetailsViewModel.onFavoriteButtonClick(image = image)
        }
    }

    private fun onDownloadButtonClick(image: Image) {
        downloadButton.setOnClickListener {
            imageDetailsViewModel.onDownloadImage(image = image)
        }
    }

    private fun fillTags(image: Image) {
        image.tags.map { tag ->
            val tagTextView = createTagTextView(tag.title)
            tagTextView.setOnClickListener {
                searchByTag(tag.title)
            }

            tagsLayout.addView(tagTextView)
        }
    }

    private fun searchByTag(title: String) {
        val searchFragment = SearchFragment()
        val args = Bundle()
        args.putString(TAG_BUNDLE_KEY, title)
        searchFragment.arguments = args

        navigation.navigate(
            mainRouter.setScreen(
                fragment = searchFragment,
                isBackStack = true
            )
        )
    }

    private fun createTagTextView(tag: String): TextView {
        val tagTextView = TextView(requireContext())
        val params = FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 10, 10)

        tagTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorTagText))
        tagTextView.setBackgroundResource(R.drawable.bg_tag)
        tagTextView.layoutParams = params
        tagTextView.textSize = 18f
        tagTextView.text = tag

        return tagTextView
    }

    private fun showLoadingError() {
        loadingError.show()
        loadingError.onRetryClick {
            imageDetailsViewModel.onStart(getImageIdFromBundle())
        }
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            is ProgressState.Loading -> progressBar.show()
            is ProgressState.Done -> progressBar.hide()
        }
    }
}