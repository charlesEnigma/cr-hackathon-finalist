package com.crunchry.animusicplayer.ui.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crunchry.animusicplayer.data.Song
import com.crunchry.animusicplayer.ui.theme.CrColors

@Composable
fun PlaylistScreen(
    songs: List<Song>,
    onBack: () -> Unit,
) {
    Scaffold(
        containerColor = CrColors.Neutral.Base,
        bottomBar = { BottomBar(currentSongTitle = songs.firstOrNull()?.title ?: "") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PlaylistHeader(onBack = onBack)
            PlaylistInfo()
            PlaylistTabs()
            HorizontalDivider(color = CrColors.Neutral.Base, thickness = 1.dp)
            SongList(songs)
        }
    }
}

@Composable
private fun PlaylistHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(CrColors.Neutral.DireWolf),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) { /* Placeholder for band member images */ }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(CrColors.Brand.Orange)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Shonen Isekai",
                    color = CrColors.Neutral.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .offset(y = (-240).dp)
                    .padding(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = CrColors.Neutral.White
                    )
                }
            }
        }
    }
}

@Composable
private fun PlaylistInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Shonen Isekai",
            color = CrColors.Neutral.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Created by Crunchyroll • 32 Songs",
            color = CrColors.Neutral.SilverChalice,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Songs recommended from the anime you watch.",
            color = CrColors.Neutral.SilverChalice,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )
        HorizontalDivider(color = CrColors.Neutral.SilverChalice.copy(alpha = 0.2f))
    }
}

@Composable
private fun PlaylistTabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        TabItem("Playlist", isSelected = true)
        Spacer(Modifier.width(24.dp))
        TabItem("Music Like This", isSelected = false)
        Spacer(Modifier.width(24.dp))
        TabItem("Anime Like This", isSelected = false)
    }
}

@Composable
private fun TabItem(title: String, isSelected: Boolean) {
    Text(
        text = title,
        color = if (isSelected) CrColors.Neutral.White else CrColors.Neutral.SilverChalice,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    )
}

@Composable
private fun SongList(songs: List<Song>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(songs) { song ->
            SongListItem(song)
        }
    }
}

@Composable
private fun SongListItem(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "Play",
            tint = CrColors.Neutral.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                color = CrColors.Neutral.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = song.artist,
                color = CrColors.Neutral.SilverChalice,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (song.isFavorite) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Favorite",
                tint = CrColors.Brand.Orange,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(12.dp))
        } else {
            Spacer(Modifier.width(32.dp))
        }

        Icon(
            Icons.Filled.MoreVert,
            contentDescription = "More Options",
            tint = CrColors.Neutral.SilverChalice,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun BottomBar(currentSongTitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(CrColors.Neutral.Base)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(CrColors.Brand.Orange)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = CrColors.Neutral.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "START LISTENING",
                    color = CrColors.Neutral.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .width(64.dp)
                    .background(CrColors.Neutral.DireWolf)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Bookmark",
                    tint = CrColors.Neutral.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
