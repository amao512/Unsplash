package com.aslnstbk.unsplash.common.data.models.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ImageTagApiData (
    @JsonProperty("title")
    val title: String?
)