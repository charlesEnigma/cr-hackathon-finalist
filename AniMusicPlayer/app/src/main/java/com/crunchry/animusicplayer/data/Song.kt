package com.crunchry.animusicplayer.data

/** Data model representing a Song item within a playlist. */
data class Song(
    val title: String,
    val artist: String,
    val isFavorite: Boolean = false,
)

/** Sample songs list used for the Shonen Isekai curated playlist UI. */
val sampleSongs = listOf(
    Song("Re:Re:", "Asian Kung-Fu Generation", isFavorite = false),
    Song("Blue Bird", "Ikimonogakari", isFavorite = true),
    Song("Fly High!!", "Burnout Syndromes", isFavorite = true),
    Song("Gurenge", "LiSA", isFavorite = false),
    Song("LOST IN PARADISE feat. AKLO", "ALI", isFavorite = false),
    Song("Kaikai Kitan", "Eve", isFavorite = false),
    Song("The Day", "Porno Graffitti", isFavorite = false),
    Song("Unravel", "TK from Ling Tosite Sigure", isFavorite = false),
)
