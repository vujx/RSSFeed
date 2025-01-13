package com.rssfeed.feature.favorites.model

sealed interface FavoritesViewEffect {

  data class ErrorOccurred(val errorMessage: String) : FavoritesViewEffect
}
