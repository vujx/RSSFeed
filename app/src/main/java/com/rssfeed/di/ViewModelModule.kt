package com.rssfeed.di

import com.rssfeed.feature.favorites.viewmodel.FavoritesViewModel
import com.rssfeed.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
  viewModelOf(::HomeViewModel)
  viewModelOf(::FavoritesViewModel)
}
