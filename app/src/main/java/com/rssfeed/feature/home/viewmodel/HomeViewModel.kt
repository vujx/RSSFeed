package com.rssfeed.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.rssfeed.R
import com.rssfeed.core.base.BaseViewModel
import com.rssfeed.core.base.ChannelUiItem
import com.rssfeed.core.base.TIMEOUT_DELAY
import com.rssfeed.core.base.toItems
import com.rssfeed.core.dictionary.Dictionary
import com.rssfeed.core.mapper.ErrorMessageMapper
import com.rssfeed.core.navigation.NavigationEvent
import com.rssfeed.core.navigation.Navigator
import com.rssfeed.domain.usecase.AddRssFeed
import com.rssfeed.domain.usecase.DeleteChannel
import com.rssfeed.domain.usecase.DoesChannelExists
import com.rssfeed.domain.usecase.IsNotificationPermissionGranted
import com.rssfeed.domain.usecase.ObserveChannels
import com.rssfeed.domain.usecase.ToggleFavoriteChannel
import com.rssfeed.domain.usecase.ToggleSubscribedChannel
import com.rssfeed.domain.validator.RssFeedUrlValidator
import com.rssfeed.feature.articles.ArticlesDestination
import com.rssfeed.feature.home.model.HomeEvent
import com.rssfeed.feature.home.model.HomeViewEffect
import com.rssfeed.feature.home.model.HomeViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
  private val observeChannels: ObserveChannels,
  private val addRssFeed: AddRssFeed,
  private val errorMessageMapper: ErrorMessageMapper,
  private val validator: RssFeedUrlValidator,
  private val dictionary: Dictionary,
  private val deleteChannels: DeleteChannel,
  private val toggleFavoriteChannel: ToggleFavoriteChannel,
  private val toggleSubscribedChannel: ToggleSubscribedChannel,
  private val isNotificationPermissionGranted: IsNotificationPermissionGranted,
  private val doesChannelExists: DoesChannelExists,
  private val navigator: Navigator,
) : BaseViewModel<HomeEvent>() {

  private val searchText = MutableStateFlow("")
  private val homeItems = MutableStateFlow<List<ChannelUiItem>>(emptyList())
  private val isLoading = MutableStateFlow(true)

  private val _viewEffect = Channel<HomeViewEffect>(Channel.BUFFERED)
  val viewEffect = _viewEffect.receiveAsFlow()

  val state = combine(
    searchText,
    homeItems,
    isLoading,
  ) { searchText, homeItems, isLoading ->
    HomeViewState(
      text = searchText,
      homeItems = homeItems,
      isLoading = isLoading,
    )
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
    initialValue = HomeViewState(),
  )

  override fun onEvent(event: HomeEvent) {
    when (event) {
      HomeEvent.ObserveSavedChannels -> observeSavedChannels()
      is HomeEvent.OnItemClicked -> handleOnItemClicked(event.link)
      HomeEvent.OnSearchButtonClick -> handleOnSearchButtonClick()
      is HomeEvent.OnSearchUpdated -> searchText.update { event.newSearch }
      is HomeEvent.OnSubscribedIconClicked -> handleOnSubscribedIconClicked(
        link = event.link,
        isSubscribed = event.isSubscribed,
      )
      is HomeEvent.OnFavoriteIconClicked -> handleOnFavoriteIconClicked(
        link = event.link,
        isFavorite = event.isFavorite,
      )
      is HomeEvent.OnDeleteIconClicked -> handleOnDeleteIconClicked(event.link)
    }
  }

  private fun observeSavedChannels() = viewModelScope.launch {
    observeChannels()
      .onEach { isLoading.update { false } }
      .collectLatest { channels ->
        homeItems.update { channels.toItems() }
      }
  }

  private fun handleOnItemClicked(link: String) = viewModelScope.launch {
    navigator.emitDestination(
      NavigationEvent.Destination(
        destination = ArticlesDestination.buildRoute(link),
      ),
    )
  }

  private fun handleOnSearchButtonClick() = viewModelScope.launch {
    isLoading.update { true }

    val url = searchText.value

    if (!validator(url)) {
      val validationUrlErrorMessage =
        dictionary.getString(R.string.home_screen_validation_url_error)
      _viewEffect.send(HomeViewEffect.ErrorOccurred(validationUrlErrorMessage))
      isLoading.update { false }
      return@launch
    }

    if (doesChannelExists(url)) {
      val channelExistsErrorMessage =
        dictionary.getString(R.string.home_screen_channel_exists_error)
      _viewEffect.send(HomeViewEffect.ErrorOccurred(channelExistsErrorMessage))
      isLoading.update { false }
      return@launch
    }

    when (val result = addRssFeed(url)) {
      is Either.Left -> {
        _viewEffect.send(HomeViewEffect.ErrorOccurred(errorMessageMapper(result.value)))
        isLoading.update { false }
      }
      is Either.Right -> searchText.update { "" }
    }
  }

  private fun handleOnDeleteIconClicked(link: String) = viewModelScope.launch {
    if (!deleteChannels(link)) {
      val failedDeleteChannelMessage =
        dictionary.getString(R.string.home_screen_failed_delete_channel_error_message)
      _viewEffect.send(HomeViewEffect.ErrorOccurred(failedDeleteChannelMessage))
    }
  }

  private fun handleOnFavoriteIconClicked(
    link: String,
    isFavorite: Boolean,
  ) = viewModelScope.launch {
    if (!toggleFavoriteChannel(link, isFavorite)) {
      val failedToggleFavoriteMessage =
        dictionary.getString(R.string.home_screen_failed_toggle_favorite_channel_error_message)
      _viewEffect.send(HomeViewEffect.ErrorOccurred(failedToggleFavoriteMessage))
    }
  }

  private fun handleOnSubscribedIconClicked(
    link: String,
    isSubscribed: Boolean,
  ) = viewModelScope.launch {
    if (!isNotificationPermissionGranted()) {
      _viewEffect.send(
        HomeViewEffect.ErrorOccurred(
          errorMessage = dictionary.getString(R.string.home_screen_notification_error_message),
        ),
      )
      return@launch
    }

    if (!toggleSubscribedChannel(link, isSubscribed)) {
      val failedToggleSubscribedMessage =
        dictionary.getString(R.string.home_screen_failed_toggle_subscribed_channel_error_message)
      _viewEffect.send(HomeViewEffect.ErrorOccurred(failedToggleSubscribedMessage))
    }
  }
}
