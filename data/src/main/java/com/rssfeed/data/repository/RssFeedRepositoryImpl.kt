package com.rssfeed.data.repository

import arrow.core.Either
import arrow.core.left
import com.rssfeed.data.api.ApiService
import com.rssfeed.data.api.model.RssFeed
import com.rssfeed.data.api.model.formatPubDate
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
          }.onRight { rssFeed ->
            if (checkIfThereAreNewArticles(rssFeed)) {
              if (channel.isSubscribed == 1L) {
                updatedSubscribedChannels.add(channel.toChannelItem())
              }
              rssFeed.channel?.toChannelEntity(
                rssFeedUrl = channel.rssFeedUrl,
                isFavorite = channel.isFavorite,
                isSubscribed = channel.isSubscribed,
              )?.let { channelEntity ->
                channelDao.insertChannel(channelEntity, channel.rssFeedUrl)
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

  override suspend fun doesChannelExits(url: String) = channelDao.doesChannelExists(url)

  private suspend fun checkIfThereAreNewArticles(rssFeed: RssFeed): Boolean {
    val latestArticlePubDate =
      articleDao.getLatestPubDateByChannelLink(rssFeed.channel?.link.orEmpty()).orEmpty()
    return rssFeed.channel?.articles?.any { article ->
      article.pubDate?.let { it.formatPubDate() > latestArticlePubDate } ?: false
    } ?: false
  }
}
