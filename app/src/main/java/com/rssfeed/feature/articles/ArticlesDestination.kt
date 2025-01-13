package com.rssfeed.feature.articles

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rssfeed.core.navigation.NavigationDestination
import com.rssfeed.feature.articles.ArticlesDestination.CHANNEL_LINK_PARAM

object ArticlesDestination : NavigationDestination<String> {

  override fun route(): String = ARTICLE__ROUTE

  override fun buildRoute(data: String): String = "$ARTICLE_ROOT/${data.encodeUrl()}"

  override val arguments: List<NamedNavArgument>
    get() = listOf(
      navArgument(CHANNEL_LINK_PARAM) { type = NavType.StringType },
    )

  const val CHANNEL_LINK_PARAM = "channelLink"
}

private const val ARTICLE_ROOT = "articles"
private const val ARTICLE__ROUTE = "$ARTICLE_ROOT/{$CHANNEL_LINK_PARAM}"
