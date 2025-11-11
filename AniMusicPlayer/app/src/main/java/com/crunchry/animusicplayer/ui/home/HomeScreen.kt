package com.crunchry.animusicplayer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crunchry.animusicplayer.ui.theme.CrMainTheme

// --- Placeholder Composable Functions (to be defined later) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar() {
    val darkBackground = Color(0xFF1A1A1A)
    TopAppBar(
        title = { }, // No title text
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = darkBackground
        ),
        actions = {
            // Placeholder for the Cast icon (top right)
            IconButton(onClick = { /* Cast action */ }) {
                Icon(
                    imageVector = Icons.Default.Menu, // Using Menu as a placeholder
                    contentDescription = "Cast",
                    tint = Color.White
                )
            }
            // Search icon (top right)
            IconButton(onClick = { /* Search action */ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        }
    )
}
@Composable
fun BottomNavBar(selectedColor: Color) {
    NavigationBar(
        containerColor = Color(0xFF141414), // Slightly darker bottom bar
        tonalElevation = 0.dp
    ) {
        // Mock Navigation Items
        val items = listOf("Home", "My Lists", "Browse", "Store", "Profile")
        items.forEachIndexed { index, screen ->
            val isSelected = index == 0 // 'Home' is selected in the image

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home, // Placeholder icon
                        contentDescription = screen
                    )
                },
                label = {
                    Text(
                        text = screen,
                        fontSize = 10.sp
                    )
                },
                selected = isSelected,
                onClick = { /* Navigate to screen */ },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = selectedColor,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}
@Composable
fun TopBanner(orangeColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), // Tall banner height
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = orangeColor.copy(alpha = 0.8f)) // Orange background placeholder
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                // Crunchyroll Music Logo Placeholder
                Text(
                    text = "Crunchyroll\nmusic",
                    color = Color.White,
                    fontSize = 28.sp,
                    // Simulate a logo by using a large, bold font
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Listen to amazing music provided by Sony\nMusic while you browse or even on the go.",
                    color = Color.White,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Explore Music Button
                Button(
                    onClick = { /* Explore Music */ },
                    colors = ButtonDefaults.buttonColors(containerColor = orangeColor),
                    shape = RoundedCornerShape(2.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text(text = "♬ EXPLORE MUSIC", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}
@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )
}
@Composable
fun MediaCard(item: MediaItem, width: androidx.compose.ui.unit.Dp, height: androidx.compose.ui.unit.Dp) {
    Column(
        modifier = Modifier
            .width(width)
            .padding(end = 8.dp)
            .clickable { /* Item Click */ }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(height)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF303030)) // Placeholder for image/video thumbnail
        ) {
            // Placeholder for the Image (e.g., using Coil/Glide for actual image)
            Box(contentAlignment = Alignment.TopEnd) {
                // Placeholder for 'Subtitled' text box
                Text(
                    text = item.subtitle,
                    color = Color.White,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(bottomStart = 4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.title, color = Color.White, fontSize = 12.sp, maxLines = 2)
    }
}
@Composable
fun ContinueWatchingCard(item: MediaItem, width: androidx.compose.ui.unit.Dp) {
    Column(
        modifier = Modifier
            .width(width)
            .padding(end = 8.dp)
            .clickable { /* Item Click */ }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(150.dp) // Consistent height for continue watching
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF303030)) // Placeholder image
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Placeholder for play icon in the middle
                Icon(
                    imageVector = Icons.Default.Menu, // Play icon placeholder
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
                // Bottom text (Episode Info)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.title, color = Color.White, fontSize = 12.sp)
                    Text(text = item.subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                }
                // Placeholder for progress bar (thin orange line at the bottom)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // 60% progress
                        .height(2.dp)
                        .background(Color(0xFFE87800))
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}
@Composable
fun PlaylistCard(item: MediaItem, size: androidx.compose.ui.unit.Dp) {
    Column(
        modifier = Modifier
            .width(size)
            .padding(end = 8.dp)
            .clickable { /* Item Click */ }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .size(size)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF303030)) // Placeholder image
        ) {
            // Image/Color for the Playlist
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.title, color = Color.White, fontSize = 12.sp, maxLines = 1)
    }
}

@Composable
fun HomeScreen() {
    CrMainTheme {
        CrunchyrollHomeScreen()
    }
}

@Composable
fun CrunchyrollHomeScreen() {
    val darkOrange = Color(0xFFE87800) // Crunchyroll's signature orange
    val darkBackground = Color(0xFF1A1A1A) // Dark background color

    // Define the mock data (as defined in Section 1)
    val mockTopPicks = List(5) { MediaItem("Item $it", "Subtitled", "") }
    val mockContinueWatching = List(3) { MediaItem("Initial D", "S1, E1", "") }
    val mockPlaylists = List(4) { MediaItem("Songs", "Classics", "") }
    val mockTrending = List(5) { MediaItem("One Piece", "Subtitled", "") }

    Scaffold(
        topBar = { HomeAppBar() },
        containerColor = darkBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. Top Banner Section (Large Image with Text and Button)
            item {
                TopBanner(darkOrange)
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
                        PlaylistCard(item = item, size = 150.dp)
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
        CrunchyrollHomeScreen()
    }
}