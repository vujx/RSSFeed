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
import com.rssfeed.feature.articles.ArticlesDestination
import com.rssfeed.feature.articles.ArticlesScreen
import com.rssfeed.ui.theme.RSSFeedTheme

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
          composable(ArticlesDestination.route()) {
            ArticlesScreen()
          }
        }
      }
    }
  }
}
