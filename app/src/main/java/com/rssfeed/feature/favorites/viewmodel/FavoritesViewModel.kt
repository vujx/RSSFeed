package com.rssfeed.feature.favorites.viewmodel

import androidx.lifecycle.viewModelScope
import com.rssfeed.R
import com.rssfeed.core.base.BaseChannelItem
import com.rssfeed.core.base.BaseViewModel
import com.rssfeed.core.base.TIMEOUT_DELAY
import com.rssfeed.core.base.toItems
import com.rssfeed.core.dictionary.Dictionary
import com.rssfeed.core.navigation.NavigationEvent
import com.rssfeed.core.navigation.Navigator
import com.rssfeed.di.APP_NAVIGATOR_QUALIFIER
import com.rssfeed.domain.usecase.ObserveFavoriteChannels
import com.rssfeed.domain.usecase.ToggleFavoriteChannel
import com.rssfeed.domain.usecase.ToggleSubscribedChannel
import com.rssfeed.feature.articles.ArticlesDestination
import com.rssfeed.feature.favorites.model.FavoritesEvent
import com.rssfeed.feature.favorites.model.FavoritesViewState
import com.rssfeed.feature.home.model.HomeViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class FavoritesViewModel(
  private val observeFavoriteChannels: ObserveFavoriteChannels,
  private val dictionary: Dictionary,
  private val toggleFavoriteChannel: ToggleFavoriteChannel,
  private val toggleSubscribedChannel: ToggleSubscribedChannel,
) : BaseViewModel<FavoritesEvent>(), KoinComponent {

  private val navigator: Navigator by inject(named(APP_NAVIGATOR_QUALIFIER))

  private val favoriteItems = MutableStateFlow<List<BaseChannelItem>>(emptyList())
  private val isLoading = MutableStateFlow(true)

  private val _viewEffect = Channel<HomeViewEffect>(Channel.BUFFERED)
  val viewEffect = _viewEffect.receiveAsFlow()

  val state = combine(
    favoriteItems,
    isLoading,
  ) { favoriteItems, isLoading ->
    FavoritesViewState(
      favoriteItems = favoriteItems,
      isLoading = isLoading,
    )
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
    initialValue = FavoritesViewState(),
  )

  override fun onEvent(event: FavoritesEvent) {
    when (event) {
      FavoritesEvent.ObserveFavoriteChannels -> observeChannels()
      is FavoritesEvent.OnItemClicked -> handleOnItemClicked(event.link)
      is FavoritesEvent.OnFavoriteIconClicked -> handleOnFavoriteIconClicked(
        link = event.link,
        isFavorite = event.isFavorite,
      )

      is FavoritesEvent.OnSubscribedIconClicked -> handleOnSubscribedIconClicked(
        link = event.link,
        isSubscribed = event.isSubscribed,
      )
    }
  }

  private fun observeChannels() = viewModelScope.launch {
    isLoading.update { true }
    observeFavoriteChannels()
      .collectLatest { channels ->
        favoriteItems.update { channels.toItems() }
        isLoading.update { false }
      }
  }

  private fun handleOnItemClicked(link: String) = viewModelScope.launch {
    navigator.emitDestination(
      NavigationEvent.Destination(
        destination = ArticlesDestination.buildRoute(link),
      ),
    )
  }

  private fun handleOnFavoriteIconClicked(
    link: String,
    isFavorite: Boolean,
  ) = viewModelScope.launch {
    if (toggleFavoriteChannel(link, isFavorite).not()) {
      val failedToggleFavoriteMessage =
        dictionary.getString(R.string.favorites_screen_failed_toggle_favorite_channel_error_message)
      _viewEffect.send(HomeViewEffect.ErrorOccurred(failedToggleFavoriteMessage))
    }
  }

  private fun handleOnSubscribedIconClicked(
    link: String,
    isSubscribed: Boolean,
  ) = viewModelScope.launch {
    if (toggleSubscribedChannel(link, isSubscribed).not()) {
      val failedToggleSubscribedMessage =
        dictionary.getString(R.string.favorites_screen_failed_toggle_subscribed_channel_error_message)
      _viewEffect.send(HomeViewEffect.ErrorOccurred(failedToggleSubscribedMessage))
    }
  }
}
