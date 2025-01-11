package com.rssfeed.data.repository

import com.rssfeed.data.schema.ArticleEntity
import com.rssfeed.data.schema.ChannelEntity
import com.rssfeed.domain.model.ArticleItem
import com.rssfeed.domain.model.ChannelItem

fun ChannelEntity.toChannelItem() = ChannelItem(
  link = link,
  title = title,
  description = description,
  imageUrl = imageUrl,
  lastBuildDate = lastBuildDate,
  isFavorite = isFavorite,
)

fun List<ChannelEntity>.toChannelItems() = map { it.toChannelItem() }

fun ArticleEntity.toArticleItem() = ArticleItem(
  link = link,
  title = title,
  description = description,
  imageUrl = imageUrl,
  pubDate = pubDate,
)

fun List<ArticleEntity>.toArticleItems() = map { it.toArticleItem() }
