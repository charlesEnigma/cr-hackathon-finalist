package com.crunchry.animusicplayer.service

import android.app.Notification
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import coil.imageLoader
import coil.request.ImageRequest
import com.crunchry.animusicplayer.AniMusicPlayerApplication
import com.crunchry.animusicplayer.R
import com.google.common.collect.ImmutableList

@UnstableApi
class CustomMediaNotificationProvider(private val context: Context) : MediaNotification.Provider {

    private val notificationId = 1001

    override fun createNotification(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        onNotificationChangedCallback: MediaNotification.Provider.Callback
    ): MediaNotification {

        val player = mediaSession.player

        val remoteViews = RemoteViews(context.packageName, R.layout.custom_notification)

        val metadata = player.mediaMetadata
        remoteViews.setTextViewText(R.id.notification_title, metadata.title ?: "No Title")
        remoteViews.setTextViewText(R.id.notification_artist, metadata.artist ?: "No Artist")

        val playPauseIconRes = if (player.isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
        remoteViews.setImageViewResource(R.id.notification_play_pause, playPauseIconRes)

        val playPauseIcon = IconCompat.createWithResource(context, playPauseIconRes)
        val playPauseAction = actionFactory.createMediaAction(mediaSession, playPauseIcon, "Play/Pause", Player.COMMAND_PLAY_PAUSE)
        remoteViews.setOnClickPendingIntent(R.id.notification_play_pause, playPauseAction.actionIntent)

        remoteViews.setImageViewResource(R.id.notification_album_art, R.mipmap.ic_launcher)
        val artworkUri = metadata.artworkUri
        if (artworkUri != null) {
            val imageLoader = context.imageLoader
            val request = ImageRequest.Builder(context)
                .data(artworkUri)
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    remoteViews.setImageViewBitmap(R.id.notification_album_art, bitmap)
                    val updatedNotification = buildNotification(mediaSession, actionFactory, remoteViews)
                    onNotificationChangedCallback.onNotificationChanged(MediaNotification(notificationId, updatedNotification))
                }
                .build()
            imageLoader.enqueue(request)
        }

        val notification = buildNotification(mediaSession, actionFactory, remoteViews)
        return MediaNotification(notificationId, notification)
    }

    private fun buildNotification(
        mediaSession: MediaSession,
        actionFactory: MediaNotification.ActionFactory,
        remoteViews: RemoteViews
    ): Notification {
        val stopIcon = IconCompat.createWithResource(context, android.R.drawable.ic_menu_delete)
        val stopAction = actionFactory.createMediaAction(mediaSession, stopIcon, "Stop", Player.COMMAND_STOP)
        return NotificationCompat.Builder(context, AniMusicPlayerApplication.MEDIA_CHANNEL_ID)
            .setCustomContentView(remoteViews)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(mediaSession.sessionActivity)
            .setDeleteIntent(stopAction.actionIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(mediaSession.player.isPlaying)
            .build()
    }

    override fun handleCustomCommand(
        session: MediaSession,
        action: String,
        extras: Bundle
    ): Boolean {
        return true
    }
}
