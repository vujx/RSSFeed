package com.rssfeed.feature.home.model

import androidx.annotation.DrawableRes
import com.rssfeed.R
import com.rssfeed.domain.model.ChannelItem

data class HomeItem(
  val channelLink: String,
  val title: String,
  val description: String,
  val imageUrl: String,
  val isFavorite: Boolean,
  val isSubscribed: Boolean,
  @DrawableRes val fallbackImage: Int = R.drawable.ic_rss_feed_fallback_image,
)

fun List<ChannelItem>.toHomeItems() = map { channelItem ->
  with(channelItem) {
    val isFavorite = isFavorite == 1L
    val isSubscribed = isSubscribed == 1L

    HomeItem(
      channelLink = channelItem.link,
      title = title,
      description = description,
      imageUrl = imageUrl,
      isFavorite = isFavorite,
      isSubscribed = isSubscribed,
    )
  }
}
