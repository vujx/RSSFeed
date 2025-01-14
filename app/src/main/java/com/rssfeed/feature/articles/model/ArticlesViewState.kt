package com.rssfeed.feature.articles.model

import com.rssfeed.domain.model.ArticleItem

data class ArticlesViewState(
  val articleItems: List<ArticleUiItem> = emptyList(),
  val isLoading: Boolean = false,
)

data class ArticleUiItem(
  val title: String,
  val description: String,
  val imageUrl: String,
  val link: String,
  val pubDate: String,
)

fun List<ArticleItem>.toItems() = map { articleItem ->
  with(articleItem) {
    ArticleUiItem(
      title = title,
      description = description,
      imageUrl = imageUrl,
      link = link,
      pubDate = pubDate,
    )
  }
}
