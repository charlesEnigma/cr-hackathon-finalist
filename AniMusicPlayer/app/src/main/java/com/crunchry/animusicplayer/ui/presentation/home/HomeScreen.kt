package com.crunchry.animusicplayer.ui.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.ui.presentation.home.viewmodel.HomeUiState
import com.crunchry.animusicplayer.ui.presentation.home.viewmodel.HomeViewModel
import com.crunchry.animusicplayer.ui.theme.CrColors
import com.crunchry.animusicplayer.ui.theme.CrMainTheme

@Composable
fun HomeScreen(navController: NavController) {
    CrMainTheme {
        CrunchyrollHomeScreen(navController = navController)
    }
}

@Composable
fun CrunchyrollHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    CrunchyrollHomeScreenContent(navController, uiState)
}

@Composable
fun CrunchyrollHomeScreenContent(
    navController: NavController,
    uiState: HomeUiState,
) {
    Scaffold(
        topBar = { HomeAppBar() },
        containerColor = CrColors.Neutral.Base
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                Text(text = uiState.error, modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    // 1. Top Banner Section
                    item {
                        TopBanner()
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // 2. Top Picks for You Section
                    item {
                        SectionHeader(title = "Top picks for you")
                        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                            items(uiState.animeRecommendations) { item ->
                                MediaCard(item = item, width = 120.dp, height = 180.dp)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // 3. Continue Watching Section
                    item {
                        SectionHeader(title = "Continue watching")
                        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                            val mockContinueWatching = List(3) { MediaItem("Initial D", "S1, E1", "") }
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
                            items(uiState.songRecommendations) { item ->
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
                            items(uiState.animeRecommendations) { item ->
                                MediaCard(item = item, width = 120.dp, height = 180.dp)
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCrunchyrollHomeScreen() {
    val mockTopPicks = List(5) { MediaItem("Item $it", "Subtitled", "") }
    val mockPlaylists = List(4) { MediaItem("Songs", "Classics", "") }
    CrMainTheme(darkTheme = true) {
        CrunchyrollHomeScreenContent(
            navController = rememberNavController(),
            uiState = HomeUiState(
                isLoading = false,
                animeRecommendations = mockTopPicks,
                songRecommendations = mockPlaylists,
            )
        )
    }
}
