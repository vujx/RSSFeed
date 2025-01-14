package com.rssfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rssfeed.core.RssFeedBottomNavigationScaffold
import com.rssfeed.core.navigation.BottomBarDestination
import com.rssfeed.core.navigation.NavigationEffect
import com.rssfeed.core.ui.theme.RSSFeedTheme
import com.rssfeed.feature.articles.ArticlesDestination
import com.rssfeed.feature.articles.ArticlesDestination.CHANNEL_LINK_PARAM
import com.rssfeed.feature.articles.ArticlesScreen

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RSSFeedTheme {
        val navController = rememberNavController()
        NavigationEffect(navController = navController)

        NavHost(
          navController = navController,
          startDestination = BottomBarDestination.route(),
        ) {
          composable(BottomBarDestination.route()) {
            RssFeedBottomNavigationScaffold()
          }
          composable(
            route = ArticlesDestination.route(),
            arguments = ArticlesDestination.arguments,
            deepLinks = ArticlesDestination.deepLinks,
          ) { backStackEntry ->
            val link = backStackEntry.arguments?.getString(CHANNEL_LINK_PARAM)
              ?: error("$CHANNEL_LINK_PARAM was not provided to articleRoute")
            ArticlesScreen(link)
          }
        }
      }
    }
  }
}
