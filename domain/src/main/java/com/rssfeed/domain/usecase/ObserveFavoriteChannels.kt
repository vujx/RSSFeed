package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class ObserveFavoriteChannels(
  private val rssFeedRepository: RssFeedRepository,
) {

  operator fun invoke() = rssFeedRepository.observeFavoriteChannels()
}
