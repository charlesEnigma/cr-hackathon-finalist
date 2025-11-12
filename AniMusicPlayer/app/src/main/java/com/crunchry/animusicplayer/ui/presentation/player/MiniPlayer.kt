package com.crunchry.animusicplayer.ui.presentation.player

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import coil.compose.AsyncImage
import com.crunchry.animusicplayer.data.ALBUM_ART_DESCRIPTION
import com.crunchry.animusicplayer.data.NEXT_BUTTON_TEXT
import com.crunchry.animusicplayer.data.PLAY_PAUSE_BUTTON_TEXT
import com.crunchry.animusicplayer.data.SONG_ARTIST_TEXT
import com.crunchry.animusicplayer.data.SONG_TITLE_TEXT

@Composable
fun MiniPlayer(
    mediaMetadata: MediaMetadata?,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPipMe: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, _ ->
                    onPipMe()
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = mediaMetadata?.artworkUri,
            contentDescription = ALBUM_ART_DESCRIPTION,
            modifier = Modifier.size(48.dp),
            placeholder = rememberVectorPainter(image = Icons.Default.MusicNote)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = mediaMetadata?.title?.toString() ?: SONG_TITLE_TEXT,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = mediaMetadata?.artist?.toString() ?: SONG_ARTIST_TEXT,
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = onPlayPauseClick) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = PLAY_PAUSE_BUTTON_TEXT
            )
        }
        IconButton(onClick = onNextClick) {
            Icon(Icons.Default.SkipNext, contentDescription = NEXT_BUTTON_TEXT)
        }
    }
}
