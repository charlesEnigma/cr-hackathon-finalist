package com.crunchry.animusicplayer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String? = null,
    val icon: ImageVector? = null
) {
    object Splash : Screen("splash")
    object Home : Screen("home", "Home", Icons.Default.Home)
    object MyList : Screen("my_list", "My Lists", Icons.AutoMirrored.Filled.List)
    object Browse : Screen("browse", "Browse", Icons.Default.Search)
    // ⭐️ ADD THE MISSING STORE DESTINATION HERE
    object Store : Screen("store", "Store", Icons.Default.ShoppingCart)
    object Profile : Screen("profile", "Profile", Icons.Default.AccountBox)
    object Playlist : Screen("playlist/{itemTitle}")

    companion object {
        val mainScreens = listOf(Home, MyList, Browse, Store, Profile)
    }
}

