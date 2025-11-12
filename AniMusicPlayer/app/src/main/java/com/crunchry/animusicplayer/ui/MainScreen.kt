package com.crunchry.animusicplayer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.crunchry.animusicplayer.SplashScreenV2
import com.crunchry.animusicplayer.data.Song
import com.crunchry.animusicplayer.navigation.Screen
import com.crunchry.animusicplayer.ui.presentation.home.HomeScreen
import com.crunchry.animusicplayer.ui.presentation.player.PlayerViewModel
import com.crunchry.animusicplayer.ui.presentation.player.VideoPlayer
import com.crunchry.animusicplayer.ui.presentation.showdetails.playlist.PlaylistScreen
import com.crunchry.animusicplayer.ui.screens.BrowseScreen
import com.crunchry.animusicplayer.ui.screens.FavoritesScreen
import com.crunchry.animusicplayer.ui.screens.ProfileScreen
import com.crunchry.animusicplayer.ui.screens.StoreScreen
import com.crunchry.animusicplayer.ui.theme.CrMainTheme

@UnstableApi
@Composable
fun MainScreen(
    isInPipMode: Boolean,
    playerViewModel: PlayerViewModel = hiltViewModel(),
) {
    val mediaController by playerViewModel.mediaController.collectAsStateWithLifecycle()
    val isPlaying by playerViewModel.isPlaying.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    val wasInPip = remember { mutableStateOf(isInPipMode) }
    LaunchedEffect(isInPipMode, isPlaying) {
        if (wasInPip.value && !isInPipMode && isPlaying) {
            navController.navigate(Screen.Playlist.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
        wasInPip.value = isInPipMode
    }

    CrMainTheme {
        if (isInPipMode) {
            Box(modifier = Modifier.fillMaxSize()){
                VideoPlayer(player = mediaController)
            }
        } else {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val showNavigationSuite =
                Screen.mainScreens.any { it.route == currentDestination?.route }

            NavigationSuiteScaffold(
                navigationSuiteItems = {
                    if (showNavigationSuite) {
                        Screen.mainScreens.forEach { screen ->
                            item(
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    screen.icon?.let {
                                        Icon(
                                            it,
                                            contentDescription = screen.label
                                        )
                                    }
                                },
                                label = { screen.label?.let { Text(it) } }
                            )
                        }
                    }
                }
            ) {
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreenV2(onFinished = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Splash.route) { inclusive = true }
                                }
                            })
                        }
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        composable(Screen.MyList.route) { FavoritesScreen() }
                        composable(Screen.Browse.route) { BrowseScreen() }
                        composable(Screen.Store.route) { StoreScreen() }
                        composable(Screen.Profile.route) { ProfileScreen() }
                        composable(Screen.Playlist.route) { backStackEntry ->
                            val itemTitle = backStackEntry.arguments?.getString("itemTitle")
                            val songs = List(10) {
                                Song(
                                    "Song $it",
                                    "Artist $it",
                                    it % 3 == 0,
                                    artworkUri = "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217".toUri(),
                                    videoUri = "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd".toUri()
                                )
                            }
                            PlaylistScreen(
                                songs = songs,
                                onBack = { navController.popBackStack() },
                                isInPipMode = isInPipMode,
                                playerViewModel = playerViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
