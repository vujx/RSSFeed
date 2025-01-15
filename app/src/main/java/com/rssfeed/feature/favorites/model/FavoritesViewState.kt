package com.rssfeed.feature.favorites.model

import com.rssfeed.core.base.ChannelUiItem

data class FavoritesViewState(
  val favoriteItems: List<ChannelUiItem> = emptyList(),
  val isLoading: Boolean = true,
)
