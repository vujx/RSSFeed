package com.rssfeed.domain.di

import com.rssfeed.domain.usecase.AddRssFeed
import com.rssfeed.domain.usecase.DeleteChannel
import com.rssfeed.domain.usecase.DoesChannelExists
import com.rssfeed.domain.usecase.IsNotificationPermissionGranted
import com.rssfeed.domain.usecase.ObserveArticles
import com.rssfeed.domain.usecase.ObserveChannels
import com.rssfeed.domain.usecase.ObserveFavoriteChannels
import com.rssfeed.domain.usecase.ShowNotification
import com.rssfeed.domain.usecase.SyncAndGetUpdatedSubscribedChannels
import com.rssfeed.domain.usecase.ToggleFavoriteChannel
import com.rssfeed.domain.usecase.ToggleSubscribedChannel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
  factoryOf(::AddRssFeed)
  factoryOf(::DeleteChannel)
  factoryOf(::DoesChannelExists)
  factoryOf(::IsNotificationPermissionGranted)
  factoryOf(::ShowNotification)
  factoryOf(::ObserveArticles)
  factoryOf(::ObserveChannels)
  factoryOf(::ObserveFavoriteChannels)
  factoryOf(::ToggleFavoriteChannel)
  factoryOf(::ToggleSubscribedChannel)
  factoryOf(::SyncAndGetUpdatedSubscribedChannels)
}
