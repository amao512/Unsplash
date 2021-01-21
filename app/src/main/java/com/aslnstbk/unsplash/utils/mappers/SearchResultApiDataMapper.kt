package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.home.data.models.SearchResult
import com.aslnstbk.unsplash.home.data.models.SearchResultApiData

class SearchResultApiDataMapper(
    private val imageApiDataMapper: ImageApiDataMapper
) {

    fun map(searchResultApiData: SearchResultApiData?): SearchResult {
        return SearchResult(
            total = searchResultApiData?.total ?: DEFAULT_INT,
            totalPages = searchResultApiData?.totalPages ?: DEFAULT_INT,
            results = imageApiDataMapper.map(searchResultApiData?.results)
        )
    }
}