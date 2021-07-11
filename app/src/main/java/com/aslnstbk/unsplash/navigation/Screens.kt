package com.aslnstbk.unsplash.navigation

import android.os.Bundle
import com.aslnstbk.unsplash.favorite_images.presentation.FavoriteImagesFragment
import com.aslnstbk.unsplash.home.presentation.HomeFragment
import com.aslnstbk.unsplash.image_details.presentation.ImageDetailsFragment
import com.aslnstbk.unsplash.search.presentation.SearchFragment
import com.aslnstbk.unsplash.utils.mappers.EMPTY_STRING
import com.github.terrakok.cicerone.androidx.FragmentScreen

const val IMAGE_ID_BUNDLE_KEY = "image_id_key"
const val QUERY_BUNDLE_KEY = "tag_bundle"

object Screens {

    fun Home() = FragmentScreen { HomeFragment() }

    fun FavoriteImages() = FragmentScreen { FavoriteImagesFragment() }

    fun Search(query: String = EMPTY_STRING) = FragmentScreen {
        val searchFragment = SearchFragment()
        val args = Bundle()
        args.putString(QUERY_BUNDLE_KEY, query)
        searchFragment.arguments = args

        searchFragment
    }

    fun ImageDetails(imageId: String) = FragmentScreen {
        val imageDetailsFragment = ImageDetailsFragment()
        val args = Bundle()
        args.putString(IMAGE_ID_BUNDLE_KEY, imageId)
        imageDetailsFragment.arguments = args

        imageDetailsFragment
    }
}