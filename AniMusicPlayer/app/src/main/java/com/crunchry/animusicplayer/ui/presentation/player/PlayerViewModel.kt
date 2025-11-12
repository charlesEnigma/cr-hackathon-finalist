package com.crunchry.animusicplayer.ui.presentation.player

import android.app.Application
import android.content.ComponentName
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.common.MediaItem as AndroidXMediaItem
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.service.MediaPlaybackService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val app: Application
) : ViewModel() {

    private val _mediaController = MutableStateFlow<MediaController?>(null)
    val mediaController = _mediaController.asStateFlow()

    private val _currentMediaItem = MutableStateFlow<MediaMetadata?>(null)
    val currentMediaItem = _currentMediaItem.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private lateinit var controllerFuture: ListenableFuture<MediaController>

    init {
        viewModelScope.launch {
            initializeMediaController()
        }
    }

    private fun initializeMediaController() {
        val sessionToken = SessionToken(app, ComponentName(app, MediaPlaybackService::class.java))
        controllerFuture = MediaController.Builder(app, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                _mediaController.value = controllerFuture.get()
                _mediaController.value?.addListener(object : Player.Listener {
                    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                        _currentMediaItem.value = mediaMetadata
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        _isPlaying.value = isPlaying
                    }
                })
            },
            MoreExecutors.directExecutor()
        )
    }

    fun addMediaItemsAndPlay(songs: List<MediaItem>) {
        val mediaItems = songs.map { item ->
            AndroidXMediaItem.Builder()
                .setUri(item.videoUri)
                .setMediaId(item.videoUri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(item.title)
                        .setArtist(item.artist)
                        .setArtworkUri(item.artworkUri.toUri())
                        .build()
                )
                .build()
        }
        _mediaController.value?.setMediaItems(mediaItems)
        _mediaController.value?.prepare()
        _mediaController.value?.play()
    }

    fun onPlayPauseClick() {
        if (_isPlaying.value) {
            _mediaController.value?.pause()
        } else {
            _mediaController.value?.play()
        }
    }

    fun onNextClick() {
        _mediaController.value?.seekToNextMediaItem()
    }

    override fun onCleared() {
        super.onCleared()
        MediaController.releaseFuture(controllerFuture)
    }
}
