package com.rssfeed.feature.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rssfeed.core.navigation.NavigationEvent
import com.rssfeed.core.navigation.Navigator
import com.rssfeed.di.APP_NAVIGATOR_QUALIFIER
import com.rssfeed.feature.articles.ArticlesDestination
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun FavoritesScreen(
  modifier: Modifier = Modifier,
  navigator: Navigator = koinInject(named(APP_NAVIGATOR_QUALIFIER)),
) {
  val coroutine = rememberCoroutineScope()

  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = "Favorites Screen",
      modifier = Modifier.clickable {
        coroutine.launch {
          navigator.emitDestination(NavigationEvent.Destination(ArticlesDestination.route()))
        }
      },
    )
  }
}
