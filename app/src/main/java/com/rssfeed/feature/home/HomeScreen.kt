package com.rssfeed.feature.home

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
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

  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { },
  )

  LaunchedEffect(key1 = Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    viewModel.onEvent(HomeEvent.ObserveSavedChannels)

    viewModel.viewEffect.collect { homeViewEffect ->
      when (homeViewEffect) {
        is HomeViewEffect.ErrorOccurred -> {
          scaffoldState.snackbarHostState.showSnackbar(
            message = homeViewEffect.errorMessage,
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
    Box {
      LazyColumn(
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize(),
      ) {
        if (state.homeItems.isEmpty() && !state.isLoading) {
          item {
            RssFeedEmptyListScreen(
              contentDescription = stringResource(id = R.string.home_screen_search_icon_content_description),
              title = stringResource(id = R.string.home_screen_empty_home_list_title),
              description = stringResource(id = R.string.home_screen_empty_home_list_description),
              modifier = Modifier
                .fillParentMaxWidth()
                .fillParentMaxHeight(0.9f),
            )
          }
        } else {
          items(
            items = state.homeItems,
            key = { homeItem -> homeItem.channelLink },
          ) { homeItem ->
            RssFeedChannelCard(
              modifier = Modifier.animateItem(),
              item = homeItem,
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
      RssFeedFadeInOutContent(
        condition = state.isLoading,
        content = {
          LoadingIndicator()
        },
      )
    }
  }
}
