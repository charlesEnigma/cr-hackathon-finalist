package com.crunchry.animusicplayer.ui.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.crunchry.animusicplayer.ui.theme.CrColors

/**
 * Home screen app bar with placeholder cast and search actions.
 *
 * @param onCastClick invoked when the cast (placeholder) icon is tapped.
 * @param onSearchClick invoked when the search icon is tapped.
 * @param modifier optional [Modifier] for the TopAppBar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onCastClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val darkBackground = CrColors.Neutral.Base
    TopAppBar(
        modifier = modifier,
        title = { /* No title per design */ },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBackground),
        actions = {
            IconButton(onClick = onCastClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Cast",
                    tint = CrColors.Neutral.White
                )
            }
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = CrColors.Neutral.White
                )
            }
        }
    )
}