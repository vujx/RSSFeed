package com.rssfeed.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rssfeed.core.navigation.BottomNavigationDestination
import com.rssfeed.core.navigation.BottomNavigationEffect
import com.rssfeed.feature.favorites.FavoritesScreen
import com.rssfeed.feature.favorites.destination.FavoritesDestination
import com.rssfeed.feature.home.HomeScreen
import com.rssfeed.feature.home.destination.HomeDestination

@Composable
fun RssFeedBottomNavigationScaffold(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
) {
  BottomNavigationEffect(navController = navController)

  Scaffold(
    modifier = modifier,
    bottomBar = {
      BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        BottomNavigationDestination.bottomNavigationItems.forEach { screen ->
          RssFeedBottomNavigationItem(
            screen = screen,
            currentDestination = currentDestination,
            navController = navController,
          )
        }
      }
    },
  ) { paddingValues ->
    NavHost(
      modifier = Modifier.padding(paddingValues),
      navController = navController,
      startDestination = HomeDestination.route(),
    ) {
      composable(HomeDestination.route()) {
        HomeScreen()
      }
      composable(FavoritesDestination.route()) {
        FavoritesScreen()
      }
    }
  }
}
