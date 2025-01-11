package com.rssfeed.di

import com.rssfeed.data.di.databaseModule
import com.rssfeed.data.di.networkModule
import com.rssfeed.data.di.repositoryModule
import com.rssfeed.domain.di.useCaseModule
import com.rssfeed.domain.di.validatorModule

val appModules = listOf(
  networkModule,
  coreModule,
  databaseModule,
  repositoryModule,
  useCaseModule,
  validatorModule,
)
