package com.rssfeed.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationEvent {

  data object NavigateBack : NavigationEvent

  data class Destination(
    val destination: String,
    val builder: NavOptionsBuilder.() -> Unit = {},
  ) : NavigationEvent

  data class Website(val url: String) : NavigationEvent
}
