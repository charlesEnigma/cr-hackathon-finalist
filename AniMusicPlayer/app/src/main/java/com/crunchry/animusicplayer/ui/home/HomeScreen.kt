package com.crunchry.animusicplayer.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crunchry.animusicplayer.ui.theme.CrMainTheme

@Composable
fun HomeScreen() {
    CrMainTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                HeroCard()
            }
            items(20) { index ->
                ListItem(index)
            }
        }
    }
}

@Composable
fun HeroCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Hero Card",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ListItem(index: Int) {
    Text(
        text = "Item #$index",
        modifier = Modifier.padding(8.dp)
    )
}
