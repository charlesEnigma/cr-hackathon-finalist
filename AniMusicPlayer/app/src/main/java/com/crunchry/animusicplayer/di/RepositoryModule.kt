package com.crunchry.animusicplayer.di

import com.crunchry.animusicplayer.network.repository.SongRecommendationRepository
import com.crunchry.animusicplayer.network.repository.SongRecommendationRepositoryImpl
import com.crunchry.animusicplayer.network.serivce.SongRecommendationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSongRecommendationRepository(songRecommendationService: SongRecommendationService): SongRecommendationRepository {
        return SongRecommendationRepositoryImpl(songRecommendationService)
    }
}
