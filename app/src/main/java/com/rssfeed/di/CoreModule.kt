package com.rssfeed.di

import androidx.work.WorkManager
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.rssfeed.core.navigation.BottomNavigatorImpl
import com.rssfeed.core.navigation.Navigator
import com.rssfeed.core.navigation.NavigatorImpl
import com.rssfeed.data.Database
import com.rssfeed.domain.dispatcher.ExecutionDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
  single {
    AndroidSqliteDriver(
      schema = Database.Schema.synchronous(),
      context = androidContext(),
      name = "database.db",
    )
  } bind SqlDriver::class
  single {
    ExecutionDispatchers(
      io = Dispatchers.IO,
      computation = Dispatchers.Default,
      main = Dispatchers.Main,
    )
  }
  factory { WorkManager.getInstance(androidContext()) }
  single<Navigator>(named(APP_NAVIGATOR_QUALIFIER)) { NavigatorImpl() }
  single<Navigator>(named(BOTTOM_NAVIGATOR_QUALIFIER)) { BottomNavigatorImpl() }
}

const val APP_NAVIGATOR_QUALIFIER = "appNavigatorQualifier"
const val BOTTOM_NAVIGATOR_QUALIFIER = "bottomNavigatorQualifier"
