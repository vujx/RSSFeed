package com.rssfeed.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.navigation.NavHostController
import com.rssfeed.di.APP_NAVIGATOR_QUALIFIER
import com.rssfeed.di.BOTTOM_NAVIGATOR_QUALIFIER
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun NavigationEffect(
  navController: NavHostController,
  navigator: Navigator = koinInject(named(APP_NAVIGATOR_QUALIFIER)),
) {
  val uriHandler = LocalUriHandler.current
  LaunchedEffect(navController) {
    navigator.navigationEvent.onEach {
      executeNavigationRequests(navController, it, uriHandler)
    }.launchIn(this)
  }
}

@Composable
fun BottomNavigationEffect(
  navController: NavHostController,
  navigator: Navigator = koinInject(named(BOTTOM_NAVIGATOR_QUALIFIER)),
) {
  val uriHandler = LocalUriHandler.current
  LaunchedEffect(navController) {
    navigator.navigationEvent.onEach {
      executeNavigationRequests(navController, it, uriHandler)
    }.launchIn(this)
  }
}

private fun executeNavigationRequests(
  navController: NavHostController,
  navigationEvent: NavigationEvent,
  uriHandler: UriHandler,
) {
  when (navigationEvent) {
    is NavigationEvent.Destination -> navController.navigate(
      route = navigationEvent.destination,
      builder = navigationEvent.builder,
    )
    NavigationEvent.NavigateBack -> navController.popBackStack()
    is NavigationEvent.Website -> runCatching {
      uriHandler.openUri(uri = navigationEvent.url)
    }
  }
}
