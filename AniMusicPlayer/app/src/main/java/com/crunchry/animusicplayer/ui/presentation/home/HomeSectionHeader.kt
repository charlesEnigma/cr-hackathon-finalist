package com.crunchry.animusicplayer.ui.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import com.crunchry.animusicplayer.ui.theme.CrColors

/** Section header text used above horizontal media lists. */
@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = CrColors.Neutral.White,
        fontSize = 18.sp,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )
}
