package com.rssfeed.feature.articles.model

data class ArticlesViewState(
  val articleItems: List<ArticleUiItem> = emptyList(),
  val isLoading: Boolean = false,
)
