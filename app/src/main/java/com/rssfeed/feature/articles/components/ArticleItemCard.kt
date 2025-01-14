package com.rssfeed.feature.articles.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rssfeed.core.components.RssFeedAsyncImage
import com.rssfeed.feature.articles.model.ArticleUiItem
import com.rssfeed.feature.articles.model.ArticlesEvent

@Composable
fun ArticleItemCard(
  articleItem: ArticleUiItem,
  onEvent: (ArticlesEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable(onClick = {
        onEvent(ArticlesEvent.OnItemClicked(articleItem.link))
      }),
    shape = MaterialTheme.shapes.small,
    elevation = 4.dp,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        RssFeedAsyncImage(
          imageUrl = articleItem.imageUrl,
          contentDescription = articleItem.title,
          modifier = Modifier
            .size(60.dp)
            .clip(MaterialTheme.shapes.small),
        )
        Column(
          modifier = Modifier.padding(horizontal = 8.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          Text(
            text = articleItem.title,
            style = MaterialTheme.typography.h1,
            maxLines = TITLE_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
          )
          Text(
            text = articleItem.description,
            style = MaterialTheme.typography.body1,
            maxLines = DESCRIPTION_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
          )
          Text(
            text = articleItem.pubDate,
            style = MaterialTheme.typography.h1,
          )
        }
      }
    }
  }
}

private const val TITLE_MAX_LINES = 5
private const val DESCRIPTION_MAX_LINES = 10
