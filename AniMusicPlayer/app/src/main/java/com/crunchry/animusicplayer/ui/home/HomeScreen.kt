package com.crunchry.animusicplayer.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crunchry.animusicplayer.R

@Composable
fun HomeScreen() {
    Scaffold(
    ) { paddingValues ->
        // Main content column
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // Makes the whole column scrollable
        ) {
            FeaturedContentSection()
            TopPicksSection()
        }
    }
}

@Composable
fun FeaturedContentSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp) // Approximate height based on image
    ) {
        // 1. Background Image/Video Placeholder
        //         Image(
        /* ...
        contentScale = ContentScale.Crop
        )

        */

        // 2. Overlay Gradient (optional, for readability)
        // ...

        // 3. Text and Buttons (Aligned to the bottom/center of the Box)
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Title and Metadata
            // ...

            // Description
            Text("Gabimaru reigns as the strongest...", maxLines = 3)

            Spacer(Modifier.height(16.dp))

            // Buttons Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    Text("Start Watching S1 E1")
                }
                Spacer(Modifier.width(8.dp))
                // Bookmark Icon Button
                // ...
            }
        }
    }
}

@Composable
fun TopPicksSection() {
    // Section Title
    Text(
        text = "Top Picks for you",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )

    // Horizontal List of Thumbnails
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(count = 5) { // Assuming 5 items for demonstration
            // Individual Thumbnail Item
            Card(
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
            ) {
                //                 Image(/* ... */)
            }
        }
    }
}