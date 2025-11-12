package com.crunchry.animusicplayer.ui.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.network.data.AnimeRecommendations
import com.crunchry.animusicplayer.network.data.SongRecommendations
import com.crunchry.animusicplayer.network.repository.SongRecommendationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val animeRecommendations: List<MediaItem> = emptyList(),
    val songRecommendations: List<MediaItem> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val songRecommendationRepository: SongRecommendationRepository
) : ViewModel() {

    private val animeFlow = songRecommendationRepository.getAnimeRecommendation(1).map { it.toMediaItems() }
    private val songFlow = songRecommendationRepository.getSongRecommendation(1).map { it.toMediaItems() }

    val uiState: StateFlow<HomeUiState> = combine(animeFlow, songFlow) { anime, songs ->
        HomeUiState(
            isLoading = false,
            animeRecommendations = anime,
            songRecommendations = songs
        )
    }
        .catch { e -> emit(HomeUiState(isLoading = false, error = e.message)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    private fun AnimeRecommendations.toMediaItems(): List<MediaItem> {
        return recommendations.map { anime ->
            MediaItem(
                title = anime.title,
                subtitle = "",
                videoUri = anime.posterTall,
                artist = "",
                artworkUri = anime.posterTall
            )
        }
    }

    private fun SongRecommendations.toMediaItems(): List<MediaItem> {
        return recommendations.map { song ->
            MediaItem(
                title = song.title,
                subtitle = song.artist,
                videoUri = song.albumArtUrl,
                artist = "",
                artworkUri = song.albumArtUrl
            )
        }
    }
}