package com.crunchry.animusicplayer.ui.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.data.continueWatching
import com.crunchry.animusicplayer.navigation.Screen
import com.crunchry.animusicplayer.ui.presentation.home.viewmodel.HomeUiState
import com.crunchry.animusicplayer.ui.presentation.home.viewmodel.HomeViewModel
import com.crunchry.animusicplayer.ui.presentation.showdetails.playlist.PlaylistViewModel
import com.crunchry.animusicplayer.ui.theme.CrColors
import com.crunchry.animusicplayer.ui.theme.CrMainTheme
import androidx.compose.runtime.remember

import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun HomeScreen(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val navGraphBackStackEntry = remember(backStackEntry) { navController.getBackStackEntry(Screen.Home.route) }
    val playlistViewModel: PlaylistViewModel = hiltViewModel(navGraphBackStackEntry)
    CrMainTheme {
        CrunchyrollHomeScreen(navController = navController, playlistViewModel = playlistViewModel)
    }
}

@Composable
fun CrunchyrollHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    playlistViewModel: PlaylistViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    CrunchyrollHomeScreenContent(navController, uiState, playlistViewModel)
}

@Composable
fun CrunchyrollHomeScreenContent(
    navController: NavController,
    uiState: HomeUiState,
    playlistViewModel: PlaylistViewModel
) {
    Scaffold(
        topBar = { HomeAppBar() },
        containerColor = CrColors.Neutral.Base
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // 1. Top Banner Section
            item {
                TopBanner()
            }
            // Apply padding to the rest of the content
            item {
                Column(modifier = Modifier) {
                    SectionHeader(title = "Top picks for you")
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                        items(uiState.animeRecommendations) { item ->
                            MediaCard(item = item, width = 120.dp, height = 180.dp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    }
                    val mockContinueWatching = continueWatching
                    SectionHeader(title = "Continue watching")
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                        items(mockContinueWatching) { item ->
                            ContinueWatchingCard(item = item, width = 280.dp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionHeader(title = "♬ Crunchyroll Curated Playlists")
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                        items(uiState.songRecommendations) { item ->
                            PlaylistCard(item = item, size = 150.dp) {
                                playlistViewModel.setPlaylist(uiState.songRecommendations)
                                navController.navigate("playlist/${item.title}")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

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


@Preview(showBackground = true)
@Composable
fun PreviewCrunchyrollHomeScreen() {
    val mockTopPicks = listOf(
        MediaItem("Item 0", "Subtitled", "", "","R.drawable.show_dan_da_dan")
    ) + List(4) { MediaItem("Item ${it + 1}", "Subtitled", "", "","R.drawable.show_dan_da_dan") }
    val mockPlaylists = List(4) { MediaItem("Songs", "Classics", "", "","R.drawable.show_dan_da_dan" ) }
    val navController = rememberNavController()
    val playlistViewModel: PlaylistViewModel = hiltViewModel()
    CrMainTheme(darkTheme = true) {
        CrunchyrollHomeScreenContent(
            navController = navController,
            uiState = HomeUiState(
                isLoading = false,
                animeRecommendations = mockTopPicks,
                songRecommendations = mockPlaylists,
            ),
            playlistViewModel = playlistViewModel
        )
    }
}
