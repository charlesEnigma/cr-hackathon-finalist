package com.crunchry.animusicplayer.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.crunchry.animusicplayer.ui.theme.CrMainTheme
import com.crunchry.animusicplayer.ui.utils.isTV

/**
 * Unified HomeScreen after merge:
 * - Mobile: LazyColumn (original list + hero + horizontal TopPicks section integrated).
 * - TV: Vertical scrolling Column (section style, can be adapted to focus navigation later).
 * - Shared FeaturedContentSection & TopPicksSection from dev branch.
 * - Retains placeholder HeroCard/ListItem from original.
 */
@Composable
fun HomeScreen() {
    CrMainTheme {
        if (isTV()) {
            TVHomeScreen()
        } else {
            MobileHomeScreen()
        }
    }
}

// ---------------- Mobile Layout (phone/tablet) ----------------
@Composable
private fun MobileHomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Hero style card from original branch
        item { HeroCard() }
        // Placeholder list items (original vertical list intent)
        items(10) { index -> ListItem(index) }
        // Featured large banner (from dev changes)
        item { FeaturedContentSection(modifier = Modifier.padding(top = 8.dp)) }
        // Horizontal carousel section
        item { TopPicksSection(modifier = Modifier.padding(top = 24.dp)) }
    }
}

// ---------------- TV Layout (Android TV) ----------------
@Composable
private fun TVHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        HeroCard()
        Spacer(Modifier.height(8.dp))
        FeaturedContentSection()
        Spacer(Modifier.height(32.dp))
        TopPicksSection()
    }
}

// ---------------- Shared Sections ----------------
@Composable
private fun FeaturedContentSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp) // Reduced height from 550 for mobile ergonomics; TV still fine.
    ) {
        // TODO: Add background image / video surface.
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                "Gabimaru reigns as the strongest...",
                maxLines = 3,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { /* TODO start playback */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Start Watching S1 E1")
                }
            }
        }
    }
}

@Composable
private fun TopPicksSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Top Picks for you",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(8) { // Placeholder count
                Card(
                    modifier = Modifier
                        .width(140.dp)
                        .height(200.dp)
                ) {
                    // TODO: Poster image placeholder
                }
            }
        }
    }
}

// ---------------- Legacy Placeholder Components ----------------
@Composable
private fun HeroCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Hero Card",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun ListItem(index: Int) {
    Text(
        text = "Item #$index",
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}
