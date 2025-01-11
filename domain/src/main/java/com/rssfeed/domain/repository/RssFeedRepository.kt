package com.rssfeed.domain.repository

import arrow.core.Either
import com.rssfeed.domain.error.RssFeedError
import com.rssfeed.domain.model.ArticleItem
import com.rssfeed.domain.model.ChannelItem
import kotlinx.coroutines.flow.Flow

interface RssFeedRepository {

  suspend fun addRssFeed(url: String): Either<RssFeedError, Unit>

  fun observeChannels(): Flow<List<ChannelItem>>

  suspend fun deleteChannel(link: String): Boolean

  suspend fun observeFavoriteChannels(): Flow<List<ChannelItem>>

  fun observeArticlesByChannelLink(channelLink: String): Flow<List<ArticleItem>>
}
