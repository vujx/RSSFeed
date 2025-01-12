package com.rssfeed.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigatorImpl : Navigator {

  private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
  override val navigationEvent = _navigationEvent.receiveAsFlow()

  override suspend fun emitDestination(event: NavigationEvent) {
    _navigationEvent.send(event)
  }
}
