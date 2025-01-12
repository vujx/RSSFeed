package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class SyncAndGetUpdatedSubscribedChannelTitles(
  private val rssFeedRepository: RssFeedRepository,
) {

  suspend operator fun invoke() = rssFeedRepository.syncAndGetUpdatedSubscribedChannelTitles()
}
