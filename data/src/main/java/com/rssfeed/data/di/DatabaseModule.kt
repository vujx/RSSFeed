package com.rssfeed.data.di

import com.rssfeed.data.Database
import org.koin.dsl.module

val databaseModule = module {
  single { Database(get()) }
}
