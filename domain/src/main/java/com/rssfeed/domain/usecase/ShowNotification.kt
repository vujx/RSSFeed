package com.rssfeed.domain.usecase

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.rssfeed.domain.R

class ShowNotification(
  private val context: Context,
) {

  operator fun invoke(
    title: String,
    body: String,
    uri: String,
  ) {
    val notificationBuilder = createNotificationBuilder(
      title = title,
      body = body,
      uri = uri,
    )
    createNotificationManager(notificationBuilder)
  }

  private fun createNotificationBuilder(
    title: String,
    body: String,
    uri: String,
  ): NotificationCompat.Builder {
    return NotificationCompat.Builder(context, CHANNEL_ID)
      .setContentTitle(title)
      .setContentText(body)
      .setSmallIcon(R.drawable.ic_notification)
      .setContentIntent(getContentIntent(uri))
  }

  private fun getContentIntent(uri: String): PendingIntent? {
    val intent = Intent().apply {
      action = Intent.ACTION_VIEW
      data = Uri.parse(uri)
    }

    return TaskStackBuilder.create(context).run {
      addNextIntentWithParentStack(intent)
      getPendingIntent(REQUEST_ID, immutableFlags())
    }
  }

  private fun immutableFlags() = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

  private fun createNotificationManager(
    notificationBuilder: NotificationCompat.Builder,
  ) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
  }
}

const val CHANNEL_ID = "contentFeedId"
const val CHANNEL_NAME = "ContentFeedNotifications"
const val REQUEST_ID = 1234
const val NOTIFICATION_ID = 1
