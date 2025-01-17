package com.rssfeed.di

import com.rssfeed.feature.articles.viewmodel.ArticlesViewModel
import com.rssfeed.feature.favorites.viewmodel.FavoritesViewModel
import com.rssfeed.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    HomeViewModel(
      observeChannels = get(),
      addRssFeed = get(),
      errorMessageMapper = get(),
      validator = get(),
      dictionary = get(),
      deleteChannels = get(),
      toggleFavoriteChannel = get(),
      toggleSubscribedChannel = get(),
      isNotificationPermissionGranted = get(),
      doesChannelExists = get(),
      navigator = get(named(APP_NAVIGATOR_QUALIFIER)),
    )
  }
  viewModel {
    FavoritesViewModel(
      observeFavoriteChannels = get(),
      dictionary = get(),
      toggleFavoriteChannel = get(),
      toggleSubscribedChannel = get(),
      isNotificationPermissionGranted = get(),
      navigator = get(named(APP_NAVIGATOR_QUALIFIER)),
    )
  }
  viewModelOf(::ArticlesViewModel)
}
