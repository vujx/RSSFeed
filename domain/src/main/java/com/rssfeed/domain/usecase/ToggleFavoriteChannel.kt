package com.rssfeed.domain.usecase

import com.rssfeed.domain.repository.RssFeedRepository

class ToggleFavoriteChannel(
  private val rssFeedRepository: RssFeedRepository,
) {

  suspend operator fun invoke(
    link: String,
    isFavorite: Boolean,
  ) = rssFeedRepository.toggleFavoriteChannel(
    link = link,
    isFavorite = if (isFavorite) 1L else 0L,
  )
}
