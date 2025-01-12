package com.rssfeed.feature.articles

import com.rssfeed.core.navigation.NavigationDestination

object ArticlesDestination : NavigationDestination<Unit> {

  override fun route(): String = "articles"
}
