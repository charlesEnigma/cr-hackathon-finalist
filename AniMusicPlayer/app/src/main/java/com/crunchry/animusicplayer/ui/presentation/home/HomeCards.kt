package com.crunchry.animusicplayer.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.crunchry.animusicplayer.R
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.ui.theme.CrColors


@Composable
fun MediaCard(item: MediaItem, width: Dp, height: Dp) {
    Column(
        modifier = Modifier
            .width(width)
            .padding(end = 8.dp)
            .clickable { /* Item Click */ }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(height)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CrColors.Neutral.DireWolf)
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                AsyncImage(
                    model = item.artworkUri,
                    error = rememberVectorPainter(Icons.Default.MusicNote),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (item.subtitle.isNotBlank()) {
                    Text(
                        text = item.subtitle,
                        color = CrColors.Neutral.White,
                        fontSize = 8.sp,
                        modifier = Modifier
                            .background(CrColors.Neutral.Black60, RoundedCornerShape(bottomStart = 4.dp))
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.title, color = CrColors.Neutral.White, fontSize = 12.sp, maxLines = 2)
    }
}

/** Card for Continue Watching row with episode progress display. */
@Composable
fun ContinueWatchingCard(item: MediaItem, width: Dp) {
    Column(
        modifier = Modifier
            .width(width)
            .padding(end = 8.dp)
            .clickable { /* Item Click */ }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(150.dp) // Consistent height for continue watching
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CrColors.Neutral.DireWolf) // Placeholder image
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = R.drawable.show_dan_da_dan,//item.imageUrl,
                    contentDescription = "Playlist Poster",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .background(CrColors.Neutral.Black60)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.title, color = CrColors.Neutral.White, fontSize = 12.sp)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // 60% progress
                        .height(2.dp)
                        .background(CrColors.Brand.OrangeDark)
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

/** Square playlist card supporting navigation click. */
@Composable
fun PlaylistCard(item: MediaItem, size: Dp, onClick: (MediaItem) -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(size)
            .padding(end = 8.dp)
            .clickable { onClick(item) }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .size(size)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CrColors.Neutral.DireWolf)
        ) {
            AsyncImage(
                model = item.artworkUri,
                error = rememberVectorPainter(Icons.Default.MusicNote),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.title, color = CrColors.Neutral.White, fontSize = 12.sp, maxLines = 1)
    }
}
