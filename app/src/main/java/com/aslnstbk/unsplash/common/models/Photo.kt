package com.aslnstbk.unsplash.common.models

data class Photo(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val promoted_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val blur_hash: String,
    val description: String,
    val alt_description: String,
    val urls: PhotoUrls,
    val links: PhotoLinks,
    val categories: Any,
    val likes: Int,
    val user: PhotoUser
)