package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.search.data.models.SearchResult
import com.aslnstbk.unsplash.search.data.models.SearchResultApiData
import com.aslnstbk.unsplash.utils.extensions.orZero

class SearchResultApiDataMapper(
    private val imageApiDataMapper: ImageApiDataMapper
) {

    fun map(searchResultApiData: SearchResultApiData?): SearchResult {
        return SearchResult(
            total = searchResultApiData?.total.orZero(),
            totalPages = searchResultApiData?.totalPages.orZero(),
            results = imageApiDataMapper.map(searchResultApiData?.results)
        )
    }
}