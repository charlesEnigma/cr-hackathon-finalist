package com.crunchry.animusicplayer.ui.presentation.showdetails.playlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.crunchry.animusicplayer.data.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PlaylistUiState(
    val playlist: MediaItem? = null,
    val songs: List<MediaItem> = emptyList(),
    val isLoaded: Boolean = false
)

@HiltViewModel
class PlaylistViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(PlaylistUiState())
        private set

    fun setPlaylist(songs: List<MediaItem>) {
        uiState = uiState.copy(songs = songs, isLoaded = true)
    }
}
