package com.rssfeed.feature.articles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.rssfeed.R
import com.rssfeed.core.components.LoadingIndicator
import com.rssfeed.core.components.RssFeedEmptyListScreen
import com.rssfeed.core.components.RssFeedFadeInOutContent
import com.rssfeed.feature.articles.components.ArticleItemCard
import com.rssfeed.feature.articles.model.ArticlesEvent
import com.rssfeed.feature.articles.viewmodel.ArticlesViewModel
import org.koin.compose.koinInject

@Composable
fun ArticlesScreen(
  channelLink: String,
  modifier: Modifier = Modifier,
  viewModel: ArticlesViewModel = koinInject(),
) {
  val state by viewModel.state.collectAsState()

  LaunchedEffect(key1 = Unit) {
    viewModel.onEvent(ArticlesEvent.ObserveArticles(channelLink))
  }

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(id = R.string.articles_screen_top_bar_title),
            style = MaterialTheme.typography.h2,
          )
        },
        navigationIcon = {
          IconButton(
            onClick = {
              viewModel.onEvent(ArticlesEvent.OnBackIconClicked)
            },
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = stringResource(id = R.string.articles_screen_back_icon_content_description),
            )
          }
        },
      )
    },
  ) { paddingValues ->
    Column {
      RssFeedFadeInOutContent(
        condition = state.isLoading,
        content = {
          LoadingIndicator()
        },
      )
      LazyColumn(
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize(),
      ) {
        if (state.articleItems.isEmpty() && !state.isLoading) {
          item {
            RssFeedEmptyListScreen(
              contentDescription = stringResource(id = R.string.articles_screen_search_icon_content_description),
              title = stringResource(id = R.string.articles_screen_empty_articles_list_title),
              description = stringResource(id = R.string.articles_screen_empty_articles_list_description),
              modifier = Modifier
                .fillParentMaxWidth()
                .fillParentMaxHeight(0.9f),
            )
          }
        } else {
          items(state.articleItems) { articleItem ->
            ArticleItemCard(
              modifier = Modifier.animateItem(),
              articleItem = articleItem,
              onEvent = viewModel::onEvent,
            )
          }
        }
      }
    }
  }
}
