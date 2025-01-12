package com.rssfeed.core

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.rssfeed.core.navigation.BottomNavigationDestination
import com.rssfeed.core.navigation.NavigationEvent
import com.rssfeed.core.navigation.Navigator
import com.rssfeed.di.BOTTOM_NAVIGATOR_QUALIFIER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun <T> RowScope.RssFeedBottomNavigationItem(
  screen: BottomNavigationDestination<T>,
  currentDestination: NavDestination?,
  navController: NavController,
  modifier: Modifier = Modifier,
  navigator: Navigator = koinInject(named(BOTTOM_NAVIGATOR_QUALIFIER)),
  coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
  BottomNavigationItem(
    modifier = modifier,
    icon = {
      Icon(
        painter = painterResource(screen.iconId),
        contentDescription = screen.route(),
        modifier = Modifier.size(20.dp),
      )
    },
    label = {
      Text(text = stringResource(id = screen.resourceId))
    },
    selected = currentDestination?.hierarchy?.any { it.route?.contains(screen.route()) ?: false } == true,
    onClick = {
      coroutineScope.launch {
        navigator.emitDestination(
          NavigationEvent.Destination(screen.route()) {
            popUpTo(navController.graph.findStartDestination().id) {
              saveState = true
            }
            launchSingleTop = true
            restoreState = true
          },
        )
      }
    },
    selectedContentColor = MaterialTheme.colors.primaryVariant,
    unselectedContentColor = MaterialTheme.colors.secondary,
  )
}
