package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class ToggleSubscribedChannel(
  private val rssFeedRepository: RssFeedRepository,
) {

  suspend operator fun invoke(
    link: String,
    isSubscribed: Boolean,
  ) = rssFeedRepository.toggleSubscribedChannel(
    link = link,
    isSubscribed = if (isSubscribed) 1L else 0L,
  )
}
