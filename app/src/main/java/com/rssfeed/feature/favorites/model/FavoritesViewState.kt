package com.rssfeed.feature.favorites.model

import com.rssfeed.core.base.BaseChannelItem

data class FavoritesViewState(
  val favoriteItems: List<BaseChannelItem> = emptyList(),
  val isLoading: Boolean = false,
)
