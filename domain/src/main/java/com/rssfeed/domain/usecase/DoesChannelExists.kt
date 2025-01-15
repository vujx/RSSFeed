package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class DoesChannelExists(
  private val rssFeedRepository: RssFeedRepository,
) {

  suspend operator fun invoke(url: String) = rssFeedRepository.doesChannelExits(url)
}
