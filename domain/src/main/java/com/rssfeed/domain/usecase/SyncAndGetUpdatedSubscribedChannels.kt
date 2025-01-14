package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class SyncAndGetUpdatedSubscribedChannels(
  private val rssFeedRepository: RssFeedRepository,
) {

  suspend operator fun invoke() = rssFeedRepository.syncAndGetUpdatedSubscribedChannels()
}
