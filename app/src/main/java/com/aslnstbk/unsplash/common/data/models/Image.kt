package com.aslnstbk.unsplash.common.data.models

data class Image(
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
    val urls: ImageUrls,
    val links: ImageLinks,
    val categories: Any,
    val likes: Int,
    val user: ImageUser
) {
    var isFavorite: Boolean = false
}