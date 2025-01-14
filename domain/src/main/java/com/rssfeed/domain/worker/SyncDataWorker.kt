package com.rssfeed.domain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rssfeed.domain.R
import com.rssfeed.domain.usecase.ShowNotification
import com.rssfeed.domain.usecase.SyncAndGetUpdatedSubscribedChannels
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.URLEncoder

class SyncDataWorker(
  private val appContext: Context,
  private val workerParams: WorkerParameters,
  private val showNotification: ShowNotification,
) : CoroutineWorker(appContext, workerParams), KoinComponent {

  private val syncAndGetUpdatedSubscribedChannels: SyncAndGetUpdatedSubscribedChannels by inject()

  override suspend fun doWork(): Result {
    val subscribedChannelTitles = syncAndGetUpdatedSubscribedChannels()

    subscribedChannelTitles.forEach { channelItem ->
      showNotification(
        title = channelItem.title,
        body = appContext.getString(R.string.notification_message),
        uri = getUri(channelItem.link),
      )
    }

    return Result.success()
  }

  private fun getUri(link: String): String {
    val articlesUri = workerParams.inputData.getString(ARTICLES_URI_KEY).orEmpty()
    val channelLinkParams = workerParams.inputData.getString(CHANNEL_LINK_PARAM_KEY).orEmpty()
    return articlesUri.replace(
      channelLinkParams,
      link.encodeUrl(),
    )
  }

  private fun String.encodeUrl(): String = URLEncoder.encode(this, "UTF-8")
}

// If you change the value of the keys, make sure to update it in the App class as well
private const val ARTICLES_URI_KEY = "articlesUri"
private const val CHANNEL_LINK_PARAM_KEY = "channelLink"
