package com.crunchry.animusicplayer.network.repository

import com.crunchry.animusicplayer.network.data.AnimeRecommendations
import com.crunchry.animusicplayer.network.data.ApiResult
import com.crunchry.animusicplayer.network.data.SongRecommendations
import com.crunchry.animusicplayer.network.serivce.SongRecommendationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SongRecommendationRepositoryImpl(
    private val service: SongRecommendationService
) : SongRecommendationRepository {
    override fun getAnimeRecommendation(userId: Int): Flow<AnimeRecommendations> = flow {
        when (val result = service.getAnimeRecommendation(userId)) {
            is ApiResult.Success -> emit(result.data)
            is ApiResult.Error -> {
                throw Exception("Api Error for Anime: ${result.message}")
            }
        }
    }

    override fun getSongRecommendation(userId: Int): Flow<SongRecommendations> = flow {
        when (val result = service.getSongRecommendation(userId)) {
            is ApiResult.Success -> emit(result.data)
            is ApiResult.Error -> {
                throw Exception("Api Error for Song: ${result.message}")
            }
        }
    }
}