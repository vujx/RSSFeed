package com.rssfeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.rssfeed.di.appModules
import com.rssfeed.domain.usecase.CHANNEL_ID
import com.rssfeed.domain.usecase.CHANNEL_NAME
import com.rssfeed.domain.worker.SyncDataWorker
import com.rssfeed.feature.articles.ARTICLES_URI
import com.rssfeed.feature.articles.ArticlesDestination
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.concurrent.TimeUnit

class App : Application(), KoinComponent {

  private val workManager: WorkManager by inject()
  private val syncDataWorkerConstraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

  override fun onCreate() {
    super.onCreate()
    initDI()
    enqueueSyncDataWorker()
    createNotificationChannel()
  }

  private fun initDI() {
    startKoin {
      if (BuildConfig.DEBUG) {
        androidLogger(Level.DEBUG)
      }
      androidContext(this@App)
      modules(appModules)
    }
  }

  private fun enqueueSyncDataWorker() {
    val inputData = Data.Builder()
      .putString(ARTICLES_URI_KEY, ARTICLES_URI)
      .putString(CHANNEL_LINK_PARAM_KEY, ArticlesDestination.CHANNEL_LINK_PARAM)
      .build()

    workManager.enqueueUniquePeriodicWork(
      SYNC_DATA_WORKER_NAME,
      ExistingPeriodicWorkPolicy.UPDATE,
      PeriodicWorkRequestBuilder<SyncDataWorker>(
        SYNC_DATA_WORKER_PERIOD,
        TimeUnit.HOURS,
      )
        .setInputData(inputData)
        .setConstraints(syncDataWorkerConstraints)
        .build(),
    )
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
      notificationManager?.run {
        val channel = NotificationChannel(
          CHANNEL_ID,
          CHANNEL_NAME,
          NotificationManager.IMPORTANCE_HIGH,
        )
        createNotificationChannel(channel)
      }
    }
  }
}

private const val SYNC_DATA_WORKER_NAME = "syncDataWorker"
private const val SYNC_DATA_WORKER_PERIOD = 24L

// If you change the value of the keys, make sure to update it in the worker as well
private const val ARTICLES_URI_KEY = "articlesUri"
private const val CHANNEL_LINK_PARAM_KEY = "channelLink"
