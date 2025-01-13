package com.rssfeed.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.rssfeed.core.components.LoadingIndicator
import com.rssfeed.core.components.RssFeedChannelCard
import com.rssfeed.core.components.RssFeedFadeInOutContent
import com.rssfeed.feature.home.components.EmptyHomeList
import com.rssfeed.feature.home.components.SearchTopBar
import com.rssfeed.feature.home.model.HomeEvent
import com.rssfeed.feature.home.model.HomeViewEffect
import com.rssfeed.feature.home.viewmodel.HomeViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel = koinInject(),
  scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
  val state by viewModel.state.collectAsState()

  LaunchedEffect(key1 = Unit) {
    viewModel.onEvent(HomeEvent.OnGetSavedRssFeeds)

    viewModel.viewEffect.collect { homeViewEffect ->
      when (homeViewEffect) {
        is HomeViewEffect.ErrorOccurred -> {
          scaffoldState.snackbarHostState.showSnackbar(
            message = homeViewEffect.errorMessage,
            duration = SnackbarDuration.Short,
          )
        }
      }
    }
  }

  Scaffold(
    modifier = modifier,
    scaffoldState = scaffoldState,
    topBar = {
      SearchTopBar(
        onEvent = viewModel::onEvent,
        searchText = { state.text },
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
        if (state.homeItems.isEmpty() && !state.isLoading) {
          item {
            EmptyHomeList(
              modifier = Modifier
                .fillParentMaxWidth()
                .fillParentMaxHeight(0.9f),
            )
          }
        } else {
          items(state.homeItems) { homeItem ->
            RssFeedChannelCard(
              homeItem = homeItem,
              onCardClick = {
                viewModel.onEvent(HomeEvent.OnItemClicked(homeItem.channelLink))
              },
              onDeleteIconClicked = {
                viewModel.onEvent(HomeEvent.OnDeleteIconClicked(homeItem.channelLink))
              },
              onFavoriteIconClicked = {
                viewModel.onEvent(
                  HomeEvent.OnFavoriteIconClicked(homeItem.channelLink, !homeItem.isFavorite),
                )
              },
              onSubscribedIconClicked = {
                viewModel.onEvent(
                  HomeEvent.OnSubscribedIconClicked(homeItem.channelLink, !homeItem.isSubscribed),
                )
              },
            )
          }
        }
      }
    }
  }
}
