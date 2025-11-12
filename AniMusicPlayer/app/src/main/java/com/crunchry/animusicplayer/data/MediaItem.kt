package com.crunchry.animusicplayer.data

import kotlinx.serialization.Serializable

@Serializable
data class MediaItem(
    val title: String,
    val subtitle: String,
    val artist: String = "",
    val song: String = "",
    val imageUrl: String,
    val isFavorite: Boolean = false
)