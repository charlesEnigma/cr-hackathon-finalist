package com.crunchry.animusicplayer.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRecommendations(
    @SerialName("recommendations")
    val recommendations: List<Anime>,
)

@Serializable
data class Anime(
    @SerialName("id")
    val animeId: String,
    @SerialName("title")
    val title: String,
    @SerialName("poster_tall")
    val posterTall: String,
    @SerialName("score")
    val score: Double,
)
