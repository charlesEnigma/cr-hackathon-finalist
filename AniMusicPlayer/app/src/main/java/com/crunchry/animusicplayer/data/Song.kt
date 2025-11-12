package com.crunchry.animusicplayer.data

import android.net.Uri
import androidx.core.net.toUri

/** Data model representing a Song item within a playlist. */
data class Song(
    val title: String,
    val artist: String,
    val isFavorite: Boolean = false,
    val artworkUri: Uri,
    val videoUri: Uri
)

/** Sample songs list used for the Shonen Isekai curated playlist UI. */
val sampleSongs = listOf(
    Song(
        title = "Re:Re:", artist = "Asian Kung-Fu Generation", isFavorite = false, artworkUri = "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/audio-141.mp4".toUri()
    ),
    Song(
        title = "Blue Bird", artist = "Ikimonogakari", isFavorite = true, artworkUri = "https://peach.blender.org/wp-content/uploads/poster_bunny_small.jpg".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4".toUri()
    ),
    Song(
        title = "Fly High!!", artist = "Burnout Syndromes", isFavorite = true, artworkUri = "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/audio-141.mp4".toUri()
    ),
    Song(
        title = "Gurenge", artist = "LiSA", isFavorite = false, artworkUri = "https://peach.blender.org/wp-content/uploads/poster_bunny_small.jpg".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4".toUri()
    ),
    Song(
        title = "LOST IN PARADISE feat. AKLO", artist = "ALI", isFavorite = false, artworkUri = "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/audio-141.mp4".toUri()
    ),
    Song(
        title = "Kaikai Kitan", artist = "Eve", isFavorite = false, artworkUri = "https://peach.blender.org/wp-content/uploads/poster_bunny_small.jpg".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4".toUri()
    ),
    Song(
        title = "The Day", artist = "Porno Graffitti", isFavorite = false, artworkUri = "https.peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/audio-141.mp4".toUri()
    ),
    Song(
        title = "Unravel", artist = "TK from Ling Tosite Sigure", isFavorite = false, artworkUri = "https://peach.blender.org/wp-content/uploads/poster_bunny_small.jpg".toUri(),
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4".toUri()
    ),
)
