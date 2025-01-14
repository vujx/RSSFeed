package com.rssfeed.feature.favorites.model

sealed interface FavoritesEvent {

  data object ObserveFavoriteChannels : FavoritesEvent

  data class OnItemClicked(val link: String) : FavoritesEvent

  data class OnSubscribedIconClicked(
    val link: String,
    val isSubscribed: Boolean,
  ) : FavoritesEvent

  data class OnFavoriteIconClicked(
    val link: String,
    val isFavorite: Boolean,
  ) : FavoritesEvent
}
