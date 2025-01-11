package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class ObserveArticles(
  private val rssFeedRepository: RssFeedRepository,
) {

  operator fun invoke(link: String) = rssFeedRepository.observeArticlesByChannelLink(link)
}
