package com.rssfeed.feature.articles.model

sealed interface ArticlesEvent {

  data class ObserveArticles(val channelLink: String) : ArticlesEvent

  data class OnItemClicked(val link: String) : ArticlesEvent

  data object OnBackIconClicked : ArticlesEvent
}
