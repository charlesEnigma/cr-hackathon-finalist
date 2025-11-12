package com.crunchry.animusicplayer.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongRecommendations(
    @SerialName("recommendations")
    val recommendations: List<Song>,
)

@Serializable
data class Song(
    @SerialName("id")
    val songId: String,
    @SerialName("title")
    val title: String,
    @SerialName("artist")
    val artist: String,
    @SerialName("thumbnail")
    val albumArtUrl: String,
    @SerialName("score")
    val score: Double,
)