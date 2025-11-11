package com.crunchry.animusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crunchry.animusicplayer.ui.theme.AniMusicPlayerTheme

class MainTvActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AniMusicPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    TvApp()
                }
            }
        }
    }
}

@Composable
fun TvApp() {
    // Simple horizontal menu with DPAD focus feedback
    val items = listOf("Home", "Favorites", "Profile")
    var focusedIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            items.forEachIndexed { index, label ->
                TvMenuCard(
                    label = label,
                    focused = index == focusedIndex,
                    onLeft = { if (focusedIndex > 0) focusedIndex-- },
                    onRight = { if (focusedIndex < items.lastIndex) focusedIndex++ }
                )
                if (index != items.lastIndex) Spacer(Modifier.width(24.dp))
            }
        }
        Spacer(Modifier.size(40.dp))
        Text(
            text = "Focused: ${items[focusedIndex]}",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TvMenuCard(
    label: String,
    focused: Boolean,
    onLeft: () -> Unit,
    onRight: () -> Unit,
) {
    val bg = if (focused) Color(0xFFFF5E00) else Color(0xFF222222)
    Column(
        modifier = Modifier
            .size(width = 200.dp, height = 120.dp)
            .background(bg, RoundedCornerShape(20.dp))
            .focusable()
            .onKeyEvent { keyEvent ->
                when (keyEvent.key) {
                    Key.DirectionLeft -> { onLeft(); true }
                    Key.DirectionRight -> { onRight(); true }
                    else -> false
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

