package com.rssfeed.feature.articles

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.rssfeed.core.navigation.NavigationDestination
import com.rssfeed.feature.articles.ArticlesDestination.CHANNEL_LINK_PARAM

object ArticlesDestination : NavigationDestination<String> {

  override fun route(): String = ARTICLE_ROUTE

  override fun buildRoute(data: String): String = "$ARTICLE_ROOT/${data.encodeUrl()}"

  override val arguments: List<NamedNavArgument>
    get() = listOf(
      navArgument(CHANNEL_LINK_PARAM) {
        type = NavType.StringType
        nullable = true
        defaultValue = null
      },
    )

  override val deepLinks: List<NavDeepLink>
    get() = listOf(
      navDeepLink { uriPattern = ARTICLES_URI },
    )

  const val CHANNEL_LINK_PARAM = "channelLink"
}

private const val ARTICLE_ROOT = "articles"
private const val ARTICLE_ROUTE = "$ARTICLE_ROOT/{$CHANNEL_LINK_PARAM}"
const val ARTICLES_URI = "rssFeed://articles_screen/{$CHANNEL_LINK_PARAM}"
