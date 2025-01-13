package com.rssfeed.feature.home.model

sealed interface HomeViewEffect {

  data class ErrorOccurred(
    val errorMessage: String,
  ) : HomeViewEffect
}
