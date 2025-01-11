package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class ObserveChannels(
  private val rssFeedRepository: RssFeedRepository,
) {

  operator fun invoke() = rssFeedRepository.observeChannels()
}
