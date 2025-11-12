package com.crunchry.animusicplayer.ui.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.network.data.AnimeRecommendations
import com.crunchry.animusicplayer.network.data.SongRecommendations
import com.crunchry.animusicplayer.network.repository.SongRecommendationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val songRecommendationRepository: SongRecommendationRepository
) : ViewModel() {

    private val _animeRecommendations = MutableStateFlow<List<MediaItem>>(emptyList())
    val animeRecommendations: StateFlow<List<MediaItem>> = _animeRecommendations.asStateFlow()

    private val _songRecommendations = MutableStateFlow<List<MediaItem>>(emptyList())
    val songRecommendations: StateFlow<List<MediaItem>> = _songRecommendations.asStateFlow()

    init {
        getAnimeRecommendations()
        getSongRecommendations()
    }

    private fun getAnimeRecommendations() {
        viewModelScope.launch {
            songRecommendationRepository.getAnimeRecommendation(1)
                .map { it.toMediaItems() }
                .onEach { _animeRecommendations.value = it }
                .launchIn(viewModelScope)
        }
    }

    private fun getSongRecommendations() {
        viewModelScope.launch {
            songRecommendationRepository.getSongRecommendation(1)
                .map { it.toMediaItems() }
                .onEach { _songRecommendations.value = it }
                .launchIn(viewModelScope)
        }
    }

    private fun AnimeRecommendations.toMediaItems(): List<MediaItem> {
        return recommendations.map { anime ->
            MediaItem(
                title = anime.title,
                subtitle = "",
                imageUrl = anime.posterUrl
            )
        }
    }

    private fun SongRecommendations.toMediaItems(): List<MediaItem> {
        return recommendations.map { song ->
            MediaItem(
                title = song.title,
                subtitle = song.artist,
                imageUrl = song.albumArtUrl
            )
        }
    }
}