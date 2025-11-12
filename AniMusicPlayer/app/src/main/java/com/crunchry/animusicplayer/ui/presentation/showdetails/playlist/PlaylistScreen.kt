import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.crunchry.animusicplayer.R
import com.crunchry.animusicplayer.data.MediaItem
import com.crunchry.animusicplayer.ui.theme.CrColors
import kotlin.math.roundToInt

import androidx.hilt.navigation.compose.hiltViewModel
import com.crunchry.animusicplayer.ui.presentation.showdetails.playlist.PlaylistViewModel

@Composable
fun PlaylistScreen(
    selectedSongTitle: String,
    onBack: () -> Unit,
    playlistViewModel: PlaylistViewModel = hiltViewModel()
) {
    val uiState = playlistViewModel.uiState
    val songs = uiState.songs
    val headerHeight = 380.dp
    val headerHeightPx = with(LocalDensity.current) { headerHeight.roundToPx().toFloat() }
    val headerOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection =
        remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource,
                ): Offset {
                    val delta = available.y
                    val newOffset = headerOffsetHeightPx.floatValue + delta
                    headerOffsetHeightPx.floatValue = newOffset.coerceIn(-headerHeightPx, 0f)
                    return Offset.Zero
                }
            }
        }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(CrColors.Neutral.Base)
            .nestedScroll(nestedScrollConnection),
    ) {
        val playlistDescription = "Songs recommended from the anime you watch."
        val playlistCreator = "Crunchyroll"

        SongList(songs, headerHeight) { song ->
        }
        CollapsingToolbar(
            onBack,
            selectedSongTitle,
            playlistDescription,
            playlistCreator,
            songs.size,
            headerOffsetHeightPx.floatValue,
            headerHeight,
        )
        if (uiState.isLoaded) {
            PlaybackButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                onPlayClick = {},
                onBookmarkClick = {}
            )
        }
    }
}

@Composable
fun HeroPoster(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = R.drawable.music_hero_asset,
            contentDescription = "Playlist Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = CrColors.Neutral.White,
            )
        }
    }
}

@Composable
fun HeroDetails(
    title: String,
    description: String,
    creator: String,
    songCount: Int,
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = title,
            color = CrColors.Neutral.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = "Created by $creator • $songCount Songs",
            color = CrColors.Neutral.SilverChalice,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp),
        )
        Text(
            text = description,
            color = CrColors.Neutral.SilverChalice,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
        )
        HorizontalDivider(color = CrColors.Neutral.SilverChalice.copy(alpha = 0.2f))
    }
}

@Composable
fun PlaylistTabs() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        TabItem("Playlist", isSelected = true)
        Spacer(Modifier.width(24.dp))
        TabItem("Music Like This", isSelected = false)
        Spacer(Modifier.width(24.dp))
        TabItem("Anime Like This", isSelected = false)
    }
}

@Composable
fun TabItem(
    title: String,
    isSelected: Boolean,
) {
    Text(
        text = title,
        color = if (isSelected) CrColors.Neutral.White else CrColors.Neutral.SilverChalice,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
    )
}

@Composable
fun SongList(item: List<MediaItem>, headerHeight: Dp, onSongClick: (MediaItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = headerHeight)
    ) {
        items(item) { song ->
            SongListItem(song, onClick = { onSongClick(song) })
        }
    }
}

@Composable
fun SongListItem(mediaItem: MediaItem, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) Color.DarkGray else Color.Transparent,
        animationSpec = tween(durationMillis = 100),
        label = "background"
    )

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null, // Disable ripple effect
                    onClick = onClick
                )
                .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "Play",
            tint = CrColors.Neutral.White,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = mediaItem.title,
                color = CrColors.Neutral.White,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = mediaItem.artist,
                color = CrColors.Neutral.SilverChalice,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        if (mediaItem.isFavorite) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Favorite",
                tint = CrColors.Brand.Orange,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(12.dp))
        } else {
            Spacer(Modifier.width(32.dp))
        }

        Icon(
            Icons.Filled.MoreVert,
            contentDescription = "More Options",
            tint = CrColors.Neutral.SilverChalice,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
fun CollapsingToolbar(
    onBack: () -> Unit,
    title: String,
    description: String,
    creator: String,
    songCount: Int,
    headerOffset: Float,
    headerHeight: Dp,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .offset { IntOffset(0, headerOffset.roundToInt()) }
            .background(CrColors.Neutral.Base)
    ) {
        HeroPoster(onBack = onBack)
        HeroDetails(
            title = title,
            description = description,
            creator = creator,
            songCount = songCount,
        )
        PlaylistTabs()
    }
}

@Composable
fun PlaybackButton(
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(CrColors.Neutral.Base),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier =
                Modifier
                    .weight(1f)
                    .background(CrColors.Brand.Orange)
                    .fillMaxHeight()
                    .clickable(onClick = onPlayClick)
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = CrColors.Neutral.Black,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "START LISTENING",
                    color = CrColors.Neutral.Black,
                    fontWeight = FontWeight.Bold,
                )
            }

            Box(
                modifier =
                Modifier
                    .width(64.dp)
                    .background(CrColors.Neutral.DireWolf)
                    .fillMaxHeight()
                    .clickable(onClick = onBookmarkClick)
                    .padding(3.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Bookmark",
                    tint = CrColors.Neutral.White,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}
