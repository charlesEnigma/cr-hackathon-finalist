package com.crunchry.animusicplayer.network.repository

import com.crunchry.animusicplayer.network.data.AnimeRecommendations
import com.crunchry.animusicplayer.network.data.SongRecommendations
import kotlinx.coroutines.flow.Flow

interface SongRecommendationRepository {
    suspend fun getAnimeRecommendation(userId: Int) : Flow<AnimeRecommendations>

    suspend fun getSongRecommendation(userId: Int) : Flow<SongRecommendations>
}