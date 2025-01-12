package com.rssfeed.core.navigation

import kotlinx.coroutines.flow.Flow

interface Navigator {

  val navigationEvent: Flow<NavigationEvent>

  suspend fun emitDestination(event: NavigationEvent)
}
