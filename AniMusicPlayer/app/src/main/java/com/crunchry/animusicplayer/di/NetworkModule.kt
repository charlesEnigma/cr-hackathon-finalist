package com.crunchry.animusicplayer.di

import com.crunchry.animusicplayer.network.KtorClientFactory
import com.crunchry.animusicplayer.network.serivce.SongRecommendationService
import com.crunchry.animusicplayer.network.serivce.SongRecommendationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return KtorClientFactory.simpleClient()
    }

    @Provides
    @Singleton
    fun provideSongRecommendationService(client: HttpClient): SongRecommendationService {
        return SongRecommendationServiceImpl(client)
    }
}
