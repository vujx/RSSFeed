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

  override suspend fun insertChannel(
    channelEntity: ChannelEntity,
  ) = channelQueries.transactionWithResult {
    with(channelEntity) {
      channelQueries.insertChannel(
        link = link,
        title = title,
        description = description,
        url = url,
        lastBuildDate = lastBuildDate,
        isFavorite = isFavorite,
      )
    }
  }

  override suspend fun deleteChannel(link: String) = channelQueries.deleteChannel(link)

  override suspend fun observeFavoriteChannels() =
    channelQueries.getFavoriteChannels().asFlow().mapToList(dispatchers.io)
}
