package com.rssfeed.di

import com.rssfeed.data.di.databaseModule
import com.rssfeed.data.di.networkModule

val appModules = listOf(
  networkModule,
  coreModule,
  databaseModule,
)
