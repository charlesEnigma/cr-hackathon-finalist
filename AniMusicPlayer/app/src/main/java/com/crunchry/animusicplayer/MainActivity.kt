package com.crunchry.animusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.crunchry.animusicplayer.ui.home.HomeScreen
import com.crunchry.animusicplayer.ui.theme.AniMusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AniMusicPlayerTheme {
                AniMusicPlayerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewScreenSizes
@Composable
fun AniMusicPlayerApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val scrollState = rememberScrollState()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { },
//                    navigationIcon = {
//                        Icon(
//                            painter = if (scrollState.value > 0) painterResource(id = R.drawable.mobile_home_scrolled) else painterResource(id = R.drawable.mobile_home),
//                            contentDescription = null
//                        )
//                    },
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = if (scrollState.value > 0) Color.Black.copy(alpha = 0.8f) else Color.Transparent
//                    )
//                )
//            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen()
                else -> Text(
                    text = "Hello ${currentDestination.label}!",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    MY_LIST("My Lists", Icons.AutoMirrored.Filled.List),
    BROWSE("Browse", Icons.Default.Search),
    // ⭐️ ADD THE MISSING STORE DESTINATION HERE
    STORE("Store", Icons.Default.ShoppingCart)
}