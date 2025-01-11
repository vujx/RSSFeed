package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class AddRssFeed(
  private val rssFeedRepository: RssFeedRepository,
) {

  suspend operator fun invoke(url: String) = rssFeedRepository.addRssFeed(url)
}
