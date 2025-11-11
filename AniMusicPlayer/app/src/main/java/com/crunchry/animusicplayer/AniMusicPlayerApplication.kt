package com.crunchry.animusicplayer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class AniMusicPlayerApplication : Application() {

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
}