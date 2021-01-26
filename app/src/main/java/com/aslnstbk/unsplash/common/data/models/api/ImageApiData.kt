package com.aslnstbk.unsplash.common.data.models.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ImageApiData(
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("created_at")
    val created_at: String?,
    @JsonProperty("updated_at")
    val updated_at: String?,
    @JsonProperty("promoted_at")
    val promoted_at: String?,
    @JsonProperty("width")
    val width: Int?,
    @JsonProperty("height")
    val height: Int?,
    @JsonProperty("color")
    val color: String?,
    @JsonProperty("blur_hash")
    val blur_hash: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("alt_description")
    val alt_description: String?,
    @JsonProperty("urls")
    val urls: ImageUrlsApiData?,
    @JsonProperty("links")
    val links: ImageLinksApiData?,
    @JsonProperty("categories")
    val categories: Any,
    @JsonProperty("likes")
    val likes: Int?,
    @JsonProperty("user")
    val user: ImageUserApiData?,
    @JsonProperty("tags")
    val tags: List<ImageTagApiData>?
)