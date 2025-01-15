package com.rssfeed.data.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.rssfeed.data.Database
import com.rssfeed.data.schema.ChannelEntity
import com.rssfeed.domain.dispatcher.ExecutionDispatchers
import kotlinx.coroutines.flow.Flow

class ChannelDaoImpl(
  database: Database,
  private val dispatchers: ExecutionDispatchers,
) : ChannelDao {

  private val channelQueries = database.channelEntityQueries

  override fun observeChannels(): Flow<List<ChannelEntity>> =
    channelQueries.getChannels().asFlow().mapToList(dispatchers.io)

  override fun getChannels(): List<ChannelEntity> =
    channelQueries.getChannels().executeAsList()

  override suspend fun insertChannel(
    channelEntity: ChannelEntity,
    rssFeedUrl: String,
  ) = channelQueries.transactionWithResult {
    with(channelEntity) {
      channelQueries.insertChannel(
        link = link,
        title = title,
        description = description,
        imageUrl = imageUrl,
        lastBuildDate = lastBuildDate,
        rssFeedUrl = rssFeedUrl,
        isFavorite = isFavorite,
        isSubscribed = isSubscribed,
      )
    }
  }

  override suspend fun deleteChannel(link: String) = channelQueries.deleteChannel(link)

  override suspend fun toggleFavoriteChannel(link: String, isFavorite: Long) =
    channelQueries.toggleFavoriteChannel(
      link = link,
      isFavorite = isFavorite,
    )

  override suspend fun toggleSubscribedChannel(link: String, isSubscribed: Long) =
    channelQueries.toggleSubscribedChannel(
      link = link,
      isSubscribed = isSubscribed,
    )

  override fun observeFavoriteChannels() =
    channelQueries.getFavoriteChannels().asFlow().mapToList(dispatchers.io)

  override suspend fun doesChannelExists(url: String): Boolean = channelQueries.transactionWithResult {
    channelQueries.doesChannelExists(url).executeAsOne()
  }
}
