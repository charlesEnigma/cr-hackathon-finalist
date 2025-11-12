package com.crunchry.animusicplayer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.crunchry.animusicplayer.SplashScreenV2
import com.crunchry.animusicplayer.data.Song
import com.crunchry.animusicplayer.navigation.Screen
import com.crunchry.animusicplayer.ui.home.HomeScreen
import com.crunchry.animusicplayer.ui.playlist.PlaylistScreen
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

        val showNavigationSuite = Screen.mainScreens.any { it.route == currentDestination?.route }

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
                        val songs = List(10) { Song("Song $it", "Artist $it", it % 3 == 0) }
                        PlaylistScreen(songs = songs, onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
