package com.rssfeed.data.db

import com.rssfeed.data.schema.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleDao {

  suspend fun insertArticle(articleEntity: ArticleEntity, channelLink: String)

  suspend fun deleteArticlesByChannelLink(channelLink: String)

  fun observeArticlesByChannelLink(channelLink: String): Flow<List<ArticleEntity>>
}
