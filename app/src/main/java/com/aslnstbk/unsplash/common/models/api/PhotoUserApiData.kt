package com.aslnstbk.unsplash.common.models.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
open class PhotoUserApiData(
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("updated_at")
    val updated_at: String?,
    @JsonProperty("username")
    val username: String?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("first_name")
    val first_name: String?,
    @JsonProperty("last_name")
    val last_name: String?,
    @JsonProperty("twitter_username")
    val twitter_username: String?,
    @JsonProperty("portfolio_url")
    val portfolio_url: String?,
    @JsonProperty("bio")
    val bio: String?,
    @JsonProperty("location")
    val location: String?,
    @JsonProperty("profile_image")
    val profile_image: UserProfileImageApiData?,
    @JsonProperty("instagram_username")
    val instagram_username: String?,
    @JsonProperty("total_collections")
    val total_collections: Int?,
    @JsonProperty("total_likes")
    val total_likes: Int?,
    @JsonProperty("total_photos")
    val total_photos: Int?,
)