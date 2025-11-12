package com.crunchry.animusicplayer.ui

import PlaylistScreen
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.crunchry.animusicplayer.SplashScreenV2
import com.crunchry.animusicplayer.navigation.Screen
import com.crunchry.animusicplayer.ui.presentation.home.HomeScreen
import com.crunchry.animusicplayer.ui.screens.BrowseScreen
import com.crunchry.animusicplayer.ui.screens.FavoritesScreen
import com.crunchry.animusicplayer.ui.screens.ProfileScreen
import com.crunchry.animusicplayer.ui.screens.StoreScreen
import com.crunchry.animusicplayer.ui.theme.CrMainTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    CrMainTheme {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val showNavigationSuite = currentDestination?.hierarchy?.any { dest ->
            Screen.mainScreens.any { it.route == dest.route } || dest.route == Screen.Playlist.route
        } == true

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
                            icon = { screen.icon?.let { Icon(it, contentDescription = screen.label) } },
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
                    modifier = Modifier//.padding(innerPadding)
                ) {
                    val inner = innerPadding
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
                    composable(
                        route = Screen.Playlist.route,
                        arguments = listOf(
                            navArgument("selectedSongTitle") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val selectedSongTitle = backStackEntry.arguments?.getString("selectedSongTitle")
                        if (selectedSongTitle != null) {
                            val navGraphBackStackEntry = remember(backStackEntry) {
                                navController.getBackStackEntry(Screen.Home.route)
                            }
                            val playlistViewModel: com.crunchry.animusicplayer.ui.presentation.showdetails.playlist.PlaylistViewModel = hiltViewModel(navGraphBackStackEntry)
                            PlaylistScreen(
                                selectedSongTitle = selectedSongTitle,
                                onBack = { navController.popBackStack() },
                                playlistViewModel = playlistViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
