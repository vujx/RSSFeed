package com.rssfeed.feature.home.model

sealed interface HomeEvent {

  data object ObserveSavedChannels : HomeEvent

  data class OnSearchUpdated(val newSearch: String) : HomeEvent

  data object OnSearchButtonClick : HomeEvent

  data class OnItemClicked(val link: String) : HomeEvent

  data class OnSubscribedIconClicked(
    val link: String,
    val isSubscribed: Boolean,
  ) : HomeEvent

  data class OnFavoriteIconClicked(
    val link: String,
    val isFavorite: Boolean,
  ) : HomeEvent

  data class OnDeleteIconClicked(val link: String) : HomeEvent
}
