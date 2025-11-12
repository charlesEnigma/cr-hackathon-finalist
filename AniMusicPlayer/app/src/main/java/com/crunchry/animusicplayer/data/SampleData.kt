package com.crunchry.animusicplayer.data

import androidx.core.net.toUri

val sampleMediaItems = listOf(
    MediaItem(
        title = "Re:Re:",
        subtitle = "Opening 1",
        artist = "Asian Kung-Fu Generation",
        artworkUri = "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217",
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/audio-141.mp4",
        isFavorite = false
    ),
    MediaItem(
        title = "Blue Bird",
        subtitle = "Opening 3",
        artist = "Ikimonogakari",
        artworkUri = "https://peach.blender.org/wp-content/uploads/poster_bunny_small.jpg",
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
        isFavorite = true
    ),
    MediaItem(
        title = "Fly High!!",
        subtitle = "Ending 2",
        artist = "Burnout Syndromes",
        artworkUri = "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217",
        videoUri = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/audio-141.mp4",
        isFavorite = true
    )
)

