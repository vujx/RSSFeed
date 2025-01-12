package com.rssfeed.core.navigation

import androidx.navigation.NamedNavArgument

fun interface NavigationDestination<T> {

  fun route(): String

  fun buildRoute(data: T): String = route()

  val arguments: List<NamedNavArgument>
    get() = emptyList()
}
