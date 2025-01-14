package com.rssfeed.data.repository

import arrow.core.Either
import arrow.core.left
import com.rssfeed.data.api.ApiService
import com.rssfeed.data.api.safeApiCall
import com.rssfeed.data.db.ArticleDao
import com.rssfeed.data.db.ChannelDao
import com.rssfeed.domain.error.RssFeedError
import com.rssfeed.domain.error.RssFeedError.UnknownError
import com.rssfeed.domain.model.ArticleItem
import com.rssfeed.domain.model.ChannelItem
import com.rssfeed.domain.repository.RssFeedRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RssFeedRepositoryImpl(
  private val apiService: ApiService,
  private val channelDao: ChannelDao,
  private val articleDao: ArticleDao,
) : RssFeedRepository {

  override suspend fun addRssFeed(url: String): Either<RssFeedError, Unit> = safeApiCall {
    apiService.addRssFeed(url)
  }.map { rssFeed ->
    val channelEntity = rssFeed.channel?.toChannelEntity(url)
      ?: return UnknownError.left()

    channelDao.insertChannel(channelEntity, url)

    rssFeed.channel.articles?.mapNotNull { article ->
      article.toArticleEntity(channelEntity.link)
    }?.forEach { articleEntity ->
      articleDao.insertArticle(articleEntity, channelEntity.link)
    }
  }

  override fun observeChannels(): Flow<List<ChannelItem>> =
    channelDao.observeChannels().map { it.toChannelItems() }

  override suspend fun deleteChannel(link: String): Boolean {
    return try {
      channelDao.deleteChannel(link)
      true
    } catch (e: Exception) {
      false
    }
  }

  override suspend fun toggleFavoriteChannel(link: String, isFavorite: Long): Boolean {
    return try {
      channelDao.toggleFavoriteChannel(link, isFavorite)
      true
    } catch (e: Exception) {
      false
    }
  }

  override suspend fun toggleSubscribedChannel(link: String, isSubscribed: Long): Boolean {
    return try {
      channelDao.toggleSubscribedChannel(link, isSubscribed)
      true
    } catch (e: Exception) {
      false
    }
  }

  override fun observeFavoriteChannels(): Flow<List<ChannelItem>> =
    channelDao.observeFavoriteChannels().map { it.toChannelItems() }

  override fun observeArticlesByChannelLink(channelLink: String): Flow<List<ArticleItem>> =
    articleDao.observeArticlesByChannelLink(channelLink).map { it.toArticleItems() }

  override suspend fun syncAndGetUpdatedSubscribedChannels(): List<ChannelItem> =
    coroutineScope<List<ChannelItem>> {
      val updatedSubscribedChannels = mutableListOf<ChannelItem>()

      channelDao.getChannels().map { channel ->
        async {
          safeApiCall {
            apiService.addRssFeed(channel.rssFeedUrl)
          }.map { rssFeed ->
            if (rssFeed.channel?.lastBuildDate != channel.lastBuildDate) {
              rssFeed.channel?.toChannelEntity(channel.rssFeedUrl)?.let { channelEntity ->
                if (channelEntity.isSubscribed == 1L) {
                  updatedSubscribedChannels.add(channelEntity.toChannelItem())
                }

                channelDao.insertChannel(channelEntity, channelEntity.link)
                articleDao.deleteArticlesByChannelLink(channelEntity.link)

                rssFeed.channel.articles?.mapNotNull { article ->
                  article.toArticleEntity(channelEntity.link)
                }?.forEach { articleEntity ->
                  articleDao.insertArticle(articleEntity, channelEntity.link)
                }
              }
            }
          }
        }
      }.awaitAll()

      return@coroutineScope updatedSubscribedChannels
    }
}
