package com.rssfeed.data.di

import com.rssfeed.data.repository.RssFeedRepositoryImpl
import com.rssfeed.domain.repository.RssFeedRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
  singleOf(::RssFeedRepositoryImpl) bind RssFeedRepository::class
}
