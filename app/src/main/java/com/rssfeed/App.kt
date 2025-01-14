package com.rssfeed

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.rssfeed.di.appModules
import com.rssfeed.domain.worker.SyncDataWorker
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.concurrent.TimeUnit

class App : Application(), KoinComponent {

  private val workManager: WorkManager by inject()

  override fun onCreate() {
    super.onCreate()
    initDI()
    enqueueSyncDataWorker()
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
    workManager.enqueueUniquePeriodicWork(
      SYNC_DATA_WORKER_NAME,
      ExistingPeriodicWorkPolicy.UPDATE,
      PeriodicWorkRequestBuilder<SyncDataWorker>(
        SYNC_DATA_WORKER_PERIOD,
        TimeUnit.HOURS,
      ).build(),
    )
  }
}

private const val SYNC_DATA_WORKER_NAME = "syncDataWorker"
private const val SYNC_DATA_WORKER_PERIOD = 24L
