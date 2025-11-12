package com.crunchry.animusicplayer.data

import kotlinx.serialization.Serializable

@Serializable
data class MediaItem(
    val title: String,
    val subtitle: String,
    val artist: String = "",
    val artworkUri: String,
    val videoUri: String = "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd",
    val isFavorite: Boolean = false
)