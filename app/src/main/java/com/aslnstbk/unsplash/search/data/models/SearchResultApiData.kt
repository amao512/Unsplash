package com.aslnstbk.unsplash.search.data.models

import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchResultApiData(
    @JsonProperty("total")
    val total: Int?,
    @JsonProperty("total_pages")
    val totalPages: Int?,
    @JsonProperty("results")
    val results: List<ImageApiData>?
)