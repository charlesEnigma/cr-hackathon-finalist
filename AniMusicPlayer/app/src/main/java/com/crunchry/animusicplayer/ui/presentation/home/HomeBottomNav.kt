package com.crunchry.animusicplayer.ui.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crunchry.animusicplayer.ui.theme.CrColors

/** Optional bottom navigation bar mock (currently unused). */
@Composable
fun BottomNavBar() {
    NavigationBar(
        containerColor = CrColors.Neutral.Woodsmoke,
        tonalElevation = 0.dp
    ) {
        val items = listOf("Home", "My Lists", "Browse", "Store", "Profile")
        items.forEachIndexed { index, screen ->
            val isSelected = index == 0
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = screen) },
                label = { Text(text = screen, fontSize = 10.sp) },
                selected = isSelected,
                onClick = { /* Navigate to screen */ },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CrColors.Brand.Orange,
                    unselectedIconColor = CrColors.Neutral.HeatherField,
                    selectedTextColor = CrColors.Brand.Orange,
                    unselectedTextColor = CrColors.Neutral.HeatherField
                )
            )
        }
    }
}
