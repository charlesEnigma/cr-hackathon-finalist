package com.crunchry.animusicplayer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AniMusicPlayerApplication : Application(), ImageLoaderFactory {

    companion object {
        const val MEDIA_CHANNEL_ID = "media_channel"
    }

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            MEDIA_CHANNEL_ID,
            "Media Playback",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}