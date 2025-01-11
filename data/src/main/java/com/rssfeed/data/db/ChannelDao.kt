package com.rssfeed.data.db

import com.rssfeed.data.schema.ChannelEntity
import kotlinx.coroutines.flow.Flow

interface ChannelDao {

  fun observeChannels(): Flow<List<ChannelEntity>>

  fun getChannels(): List<ChannelEntity>

  suspend fun insertChannel(
    channelEntity: ChannelEntity,
    rssFeedUrl: String,
  )

  suspend fun deleteChannel(link: String)

  fun observeFavoriteChannels(): Flow<List<ChannelEntity>>
}
