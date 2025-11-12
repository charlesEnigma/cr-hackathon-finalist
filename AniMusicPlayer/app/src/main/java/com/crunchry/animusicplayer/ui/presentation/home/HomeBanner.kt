package com.crunchry.animusicplayer.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.crunchry.animusicplayer.R
import com.crunchry.animusicplayer.ui.theme.CrColors

@Composable
fun BannerDetails() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Text(
            text = "Crunchyroll\nmusic",
            color = CrColors.Neutral.White,
            fontSize = 28.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Listen to amazing music provided by Sony\nMusic while you browse or even on the go.",
            color = CrColors.Neutral.White,
            fontSize = 12.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* Explore Music */ },
            colors = ButtonDefaults.buttonColors(containerColor = CrColors.Brand.Orange),
            shape = RoundedCornerShape(2.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(text = "♬ EXPLORE MUSIC", color = CrColors.Neutral.White, fontSize = 12.sp)
        }
    }
}

/** Top banner with Crunchyroll music marketing content. */
@Composable
fun TopBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = CrColors.Brand.Orange.copy(alpha = 0.8f))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = R.drawable.top_browse_music_asset,
                contentDescription = "Playlist Poster",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color.Black.copy(alpha = 0.6f),
                                0.5f to Color.Transparent,
                                1.0f to CrColors.Neutral.Black90
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
            ) {
                BannerDetails()
            }
        }
    }
}


