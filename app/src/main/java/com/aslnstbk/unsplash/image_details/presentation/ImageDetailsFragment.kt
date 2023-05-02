package com.aslnstbk.unsplash.image_details.presentation

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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
import com.aslnstbk.unsplash.databinding.FragmentImageDetailsBinding
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.navigation.IMAGE_ID_BUNDLE_KEY
import com.aslnstbk.unsplash.navigation.Screens
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show
import com.aslnstbk.unsplash.utils.mappers.EMPTY_STRING
import com.github.terrakok.cicerone.Router
import org.apmem.tools.layouts.FlowLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EMAIL_TEXT_FORMAT = "@%s"

class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val binding: FragmentImageDetailsBinding by viewBinding()

    private val imageDetailsViewModel: ImageDetailsViewModel by viewModel()
    private val router: Router by inject()
    private val imageLoader: ImageLoader by inject()
    private val imageViewer: ImageViewer by inject()
    private val loadingError: LoadingError by inject()

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageDetailsViewModel.onStart(getImageIdFromBundle())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        buildToolbar()
        observeLiveData()
        view.hide()
    }

    private fun getImageIdFromBundle(): String {
        return arguments?.getString(IMAGE_ID_BUNDLE_KEY, EMPTY_STRING) ?: EMPTY_STRING
    }

    private fun initViews() {
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
            router.exit()
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

    private fun fillData(image: Image) = with(binding) {
        fragmentImageDetailsImageOwnerFullName.text = image.user.name
        fragmentImageDetailsImageOwnerEmail.text = EMAIL_TEXT_FORMAT.format(image.user.username)

        loadImages(image = image)
        setFavoriteImage(isFavorite = image.isFavorite)
        onFavoriteButtonClick(image = image)
        onDownloadButtonClick(image = image)
        fillTags(image = image)

        loadingError.hide()
        view?.show()
    }

    private fun loadImages(image: Image) = with(binding) {
        imageLoader.load(
            url = image.user.profile_photo.small,
            target = fragmentImageDetailsImageOwnerPhoto,
            withCenterCrop = false
        )
        imageLoader.load(
            url = image.urls.regular,
            target = fragmentImageDetailsImage,
            withCenterCrop = false
        )

        fragmentImageDetailsImage.setOnClickListener {
            imageViewer.show(
                context = requireContext(),
                images = listOf(image.urls.regular)
            )
        }
    }

    private fun setFavoriteImage(isFavorite: Boolean) = if (isFavorite) {
        binding.fragmentImageDetailsButtonFavorite.setBackgroundResource(R.drawable.ic_favorite_bold_red)
    } else {
        binding.fragmentImageDetailsButtonFavorite.setBackgroundResource(R.drawable.ic_favorite_border)
    }

    private fun onFavoriteButtonClick(image: Image) {
        binding.fragmentImageDetailsButtonFavorite.setOnClickListener {
            imageDetailsViewModel.onFavoriteButtonClick(image = image)
        }
    }

    private fun onDownloadButtonClick(image: Image) {
        binding.fragmentImageDetailsButtonDownload.setOnClickListener {
            imageDetailsViewModel.onDownloadImage(image = image)
        }
    }

    private fun fillTags(image: Image) {
        binding.fragmentImageDetailsTags.removeAllViews()

        image.tags.map { tag ->
            val tagTextView = createTagTextView(tag.title)
            tagTextView.setOnClickListener {
                searchByTag(tag.title)
            }

            binding.fragmentImageDetailsTags.addView(tagTextView)
        }
    }

    private fun searchByTag(title: String) {
        router.navigateTo(Screens.Search(query = title))
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