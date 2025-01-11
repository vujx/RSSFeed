package com.rssfeed.domain.model

data class ChannelItem(
  val link: String,
  val title: String,
  val description: String,
  val imageUrl: String,
  val lastBuildDate: String,
  val isFavorite: Long,
)
