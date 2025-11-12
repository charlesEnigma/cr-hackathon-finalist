package com.crunchry.animusicplayer.network.repository

import android.util.Log
import com.crunchry.animusicplayer.network.data.AnimeRecommendations
import com.crunchry.animusicplayer.network.data.SongRecommendations
import com.crunchry.animusicplayer.network.data.asFlow
import com.crunchry.animusicplayer.network.serivce.SongRecommendationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class SongRecommendationRepositoryImpl(
    private val service: SongRecommendationService
) : SongRecommendationRepository {
    override suspend fun getAnimeRecommendation(userId: Int): Flow<AnimeRecommendations> =
        service.getAnimeRecommendation(userId).asFlow()
            .onEach { Log.d("SongRecommendationRepo", "Anime recommendation: $it") }


    override suspend fun getSongRecommendation(userId: Int): Flow<SongRecommendations> =
        service.getSongRecommendation(userId).asFlow()
            .onEach { Log.d("SongRecommendationRepo", "Song recommendation: $it") }
}