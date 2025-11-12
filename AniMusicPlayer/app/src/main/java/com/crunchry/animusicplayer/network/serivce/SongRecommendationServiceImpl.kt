package com.crunchry.animusicplayer.network.serivce

import android.util.Log
import com.crunchry.animusicplayer.network.data.AnimeRecommendations
import com.crunchry.animusicplayer.network.data.ApiResult
import com.crunchry.animusicplayer.network.data.SongRecommendations
import com.crunchry.animusicplayer.network.data.UserBody
import com.crunchry.animusicplayer.network.data.getSuccessOrNull
import com.crunchry.animusicplayer.network.sendPost
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SongRecommendationServiceImpl(
    private val client: HttpClient
) : SongRecommendationService {
    override suspend fun getAnimeRecommendation(userId: Int): ApiResult<AnimeRecommendations> {
        val result = client.sendPost<AnimeRecommendations>(path = "recommend/anime") {
            contentType(ContentType.Application.Json)
            setBody(UserBody(userId))
        }
        Log.d("SongRecommendationService", "Anime recommendation result: $result")
        return result
    }

    override suspend fun getSongRecommendation(userId: Int): ApiResult<SongRecommendations> {
        val result = client.sendPost<SongRecommendations>(path = "recommend/songs") {
            contentType(ContentType.Application.Json)
            setBody(UserBody(userId))
        }
        Log.d("SongRecommendationService", "Song recommendation result: $result")
        return result
    }
}