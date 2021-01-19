package com.aslnstbk.unsplash.common.models.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserProfileImageApiData(
    @JsonProperty("small")
    val small: String?,
    @JsonProperty("medium")
    val medium: String?,
    @JsonProperty("large")
    val large: String?
)