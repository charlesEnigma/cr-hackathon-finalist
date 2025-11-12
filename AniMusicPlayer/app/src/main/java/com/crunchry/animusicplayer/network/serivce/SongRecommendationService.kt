package com.crunchry.animusicplayer.network.serivce

import com.crunchry.animusicplayer.network.data.AnimeRecommendations
import com.crunchry.animusicplayer.network.data.ApiResult
import com.crunchry.animusicplayer.network.data.SongRecommendations

interface SongRecommendationService {

    suspend fun getAnimeRecommendation(userId: Int) : ApiResult<AnimeRecommendations>

    suspend fun getSongRecommendation(userId: Int) : ApiResult<SongRecommendations>
}