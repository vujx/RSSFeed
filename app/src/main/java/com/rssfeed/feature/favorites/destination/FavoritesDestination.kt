package com.rssfeed.feature.favorites.destination

import com.rssfeed.R
import com.rssfeed.core.navigation.BottomNavigationDestination

object FavoritesDestination : BottomNavigationDestination<Nothing>(
  resourceId = R.string.favorites_screen_bottom_navigation_title,
  iconId = R.drawable.ic_favorite,
) {

  override fun route(): String = "favorites"
}
