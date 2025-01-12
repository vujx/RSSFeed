package com.rssfeed.domain.di

import com.rssfeed.domain.usecase.AddRssFeed
import com.rssfeed.domain.usecase.DeleteChannel
import com.rssfeed.domain.usecase.ObserveArticles
import com.rssfeed.domain.usecase.ObserveChannels
import com.rssfeed.domain.usecase.ObserveFavoriteChannels
import com.rssfeed.domain.usecase.SyncAndGetUpdatedSubscribedChannelTitles
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
  factoryOf(::AddRssFeed)
  factoryOf(::DeleteChannel)
  factoryOf(::ObserveArticles)
  factoryOf(::ObserveChannels)
  factoryOf(::ObserveFavoriteChannels)
  factoryOf(::SyncAndGetUpdatedSubscribedChannelTitles)
}
