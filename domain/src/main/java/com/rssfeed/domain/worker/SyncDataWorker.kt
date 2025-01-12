package com.rssfeed.domain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rssfeed.domain.usecase.SyncAndGetUpdatedSubscribedChannelTitles
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncDataWorker(
  appContext: Context,
  workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams), KoinComponent {

  private val syncAndGetUpdatedSubscribedChannelTitles: SyncAndGetUpdatedSubscribedChannelTitles by inject()

  override suspend fun doWork(): Result {
    val subscribedChannelTitles = syncAndGetUpdatedSubscribedChannelTitles()

    subscribedChannelTitles.forEach { _ ->
      // show notification
    }

    return Result.success()
  }
}
