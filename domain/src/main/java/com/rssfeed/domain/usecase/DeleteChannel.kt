package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class DeleteChannel(
  private val rssFeedRepository: RssFeedRepository,
) {

  suspend operator fun invoke(link: String) = rssFeedRepository.deleteChannel(link)
}
