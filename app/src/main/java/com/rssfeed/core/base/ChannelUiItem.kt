package com.rssfeed.core.base

import com.rssfeed.domain.model.ChannelItem

data class ChannelUiItem(
  val channelLink: String,
  val title: String,
  val description: String,
  val imageUrl: String,
  val isFavorite: Boolean,
  val isSubscribed: Boolean,
)

fun List<ChannelItem>.toItems() = map { channelItem ->
  with(channelItem) {
    val isFavorite = isFavorite == 1L
    val isSubscribed = isSubscribed == 1L

    ChannelUiItem(
      channelLink = channelItem.link,
      title = title.trim(),
      description = description.trim(),
      imageUrl = imageUrl,
      isFavorite = isFavorite,
      isSubscribed = isSubscribed,
    )
  }
}
