package com.rssfeed.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.rssfeed.data.Database
import com.rssfeed.domain.dispatcher.ExecutionDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
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
}
