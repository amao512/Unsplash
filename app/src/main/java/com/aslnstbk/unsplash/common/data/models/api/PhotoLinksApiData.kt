package com.aslnstbk.unsplash.common.data.models.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
open class PhotoLinksApiData(
    @JsonProperty("self")
    val self: String?,
    @JsonProperty("html")
    val html: String?,
    @JsonProperty("download")
    val download: String?,
    @JsonProperty("download_location")
    val download_location: String?
)