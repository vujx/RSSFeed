package com.rssfeed.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rssfeed.feature.favorites.destination.FavoritesDestination
import com.rssfeed.feature.home.destination.HomeDestination

abstract class BottomNavigationDestination<T>(
  @StringRes val resourceId: Int,
  @DrawableRes val iconId: Int,
) : NavigationDestination<T> {

  companion object {
    val bottomNavigationItems = listOf(
      HomeDestination,
      FavoritesDestination,
    )
  }
}
