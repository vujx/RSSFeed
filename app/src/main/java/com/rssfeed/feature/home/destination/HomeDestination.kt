package com.rssfeed.feature.home.destination

import com.rssfeed.R
import com.rssfeed.core.navigation.BottomNavigationDestination

object HomeDestination : BottomNavigationDestination<Nothing>(
  resourceId = R.string.home_screen_bottom_navigation_title,
  iconId = R.drawable.ic_home,
) {

  override fun route(): String = "home"
}
