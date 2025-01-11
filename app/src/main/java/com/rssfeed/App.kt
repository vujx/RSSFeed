package com.rssfeed

import android.app.Application
import com.rssfeed.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application(), KoinComponent {

  override fun onCreate() {
    super.onCreate()
    initDI()
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
}
