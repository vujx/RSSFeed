package com.rssfeed.data.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.rssfeed.data.Database
import com.rssfeed.data.schema.ArticleEntity
import com.rssfeed.domain.dispatcher.ExecutionDispatchers
import kotlinx.coroutines.flow.Flow

class ArticleDaoImpl(
  database: Database,
  private val dispatchers: ExecutionDispatchers,
) : ArticleDao {

  private val articleQueries = database.articleEntityQueries

  override suspend fun insertArticle(
    articleEntity: ArticleEntity,
    channelLink: String,
  ) = with(articleEntity) {
    articleQueries.insertArticle(
      title = title,
      description = description,
      link = link,
      pubDate = pubDate,
      imageUrl = imageUrl,
      channelLink = channelLink,
    )
  }

  override suspend fun deleteArticlesByChannelLink(channelLink: String) =
    articleQueries.deleteArticlesByChannelLink(channelLink)

  override fun observeArticlesByChannelLink(channelLink: String): Flow<List<ArticleEntity>> =
    articleQueries.getArticlesByChannelLink(channelLink).asFlow().mapToList(dispatchers.io)
}
