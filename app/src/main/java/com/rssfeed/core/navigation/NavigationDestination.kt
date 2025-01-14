package com.rssfeed.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import java.net.URLEncoder

fun interface NavigationDestination<T> {

  fun route(): String

  fun buildRoute(data: T): String = route()

  val arguments: List<NamedNavArgument>
    get() = emptyList()

  fun String.encodeUrl(): String = URLEncoder.encode(this, "UTF-8")

  val deepLinks: List<NavDeepLink>
    get() = emptyList()
}
