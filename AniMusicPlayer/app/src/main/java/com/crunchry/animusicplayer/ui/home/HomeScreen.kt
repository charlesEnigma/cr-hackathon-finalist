package com.crunchry.animusicplayer.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.ui.theme.CrColors
import com.crunchry.animusicplayer.ui.theme.CrMainTheme

@Composable
fun HomeScreen(navController: NavController) {
    CrMainTheme {
        CrunchyrollHomeScreen(navController = navController)
    }
}

@Composable
fun CrunchyrollHomeScreen(navController: NavController) {
    // Define the mock data (as defined in Section 1)
    val mockTopPicks = List(5) { MediaItem("Item $it", "Subtitled", "") }
    val mockContinueWatching = List(3) { MediaItem("Initial D", "S1, E1", "") }
    val mockPlaylists = List(4) { MediaItem("Songs", "Classics", "") }
    val mockTrending = List(5) { MediaItem("One Piece", "Subtitled", "") }

    Scaffold(
        topBar = { HomeAppBar() },
        containerColor = CrColors.Neutral.Base
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. Top Banner Section (Large Image with Text and Button)
            item {
                TopBanner()
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 2. Top Picks for You Section
            item {
                SectionHeader(title = "Top picks for you")
                LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                    items(mockTopPicks) { item ->
                        MediaCard(item = item, width = 120.dp, height = 180.dp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 3. Continue Watching Section
            item {
                SectionHeader(title = "Continue watching")
                LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                    items(mockContinueWatching) { item ->
                        ContinueWatchingCard(item = item, width = 280.dp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 4. Crunchyroll Curated Playlists Section
            item {
                SectionHeader(title = "♬ Crunchyroll Curated Playlists")
                LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                    items(mockPlaylists) { item ->
                        PlaylistCard(item = item, size = 150.dp) {
                            navController.navigate("playlist/${item.title}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 5. Trending in the United States Section
            item {
                SectionHeader(title = "Trending in the United States")
                LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                    items(mockTrending) { item ->
                        MediaCard(item = item, width = 120.dp, height = 180.dp)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCrunchyrollHomeScreen() {
    CrMainTheme(darkTheme = true) {
        CrunchyrollHomeScreen(navController = rememberNavController())
    }
}
