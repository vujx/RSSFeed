package com.rssfeed.feature.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.rssfeed.R
import com.rssfeed.core.components.LoadingIndicator
import com.rssfeed.core.components.RssFeedChannelCard
import com.rssfeed.core.components.RssFeedEmptyListScreen
import com.rssfeed.core.components.RssFeedFadeInOutContent
import com.rssfeed.feature.favorites.model.FavoritesEvent
import com.rssfeed.feature.favorites.viewmodel.FavoritesViewModel
import com.rssfeed.feature.home.model.HomeViewEffect
import org.koin.compose.koinInject

@Composable
fun FavoritesScreen(
  modifier: Modifier = Modifier,
  viewModel: FavoritesViewModel = koinInject(),
  scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
  val state by viewModel.state.collectAsState()

  LaunchedEffect(key1 = Unit) {
    viewModel.onEvent(FavoritesEvent.ObserveFavoriteChannels)

    viewModel.viewEffect.collect { favoritesViewEffect ->
      when (favoritesViewEffect) {
        is HomeViewEffect.ErrorOccurred -> {
          scaffoldState.snackbarHostState.showSnackbar(
            message = favoritesViewEffect.errorMessage,
          )
        }
      }
    }
  }

  Scaffold(
    modifier = modifier,
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(id = R.string.favorites_screen_top_bar_title),
            style = MaterialTheme.typography.h2,
          )
        },
      )
    },
    snackbarHost = {
      SnackbarHost(
        hostState = it,
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
        if (state.favoriteItems.isEmpty() && !state.isLoading) {
          item {
            RssFeedEmptyListScreen(
              title = stringResource(id = R.string.favorites_screen_empty_favorites_list_title),
              description = stringResource(id = R.string.favorites_screen_empty_favorites_list_description),
              contentDescription = stringResource(id = R.string.favorites_screen_favorite_icon_content_description),
              imageVector = Icons.Default.Favorite,
              modifier = Modifier
                .fillParentMaxWidth()
                .fillParentMaxHeight(0.9f),
            )
          }
        } else {
          items(state.favoriteItems) { homeItem ->
            RssFeedChannelCard(
              item = homeItem,
              onCardClick = {
                viewModel.onEvent(FavoritesEvent.OnItemClicked(homeItem.channelLink))
              },
              onFavoriteIconClicked = {
                viewModel.onEvent(
                  FavoritesEvent.OnFavoriteIconClicked(homeItem.channelLink, !homeItem.isFavorite),
                )
              },
              onSubscribedIconClicked = {
                viewModel.onEvent(
                  FavoritesEvent.OnSubscribedIconClicked(homeItem.channelLink, !homeItem.isSubscribed),
                )
              },
            )
          }
        }
      }
    }
  }
}
