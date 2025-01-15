package com.rssfeed.feature.articles.viewmodel

import androidx.lifecycle.viewModelScope
import com.rssfeed.core.base.BaseViewModel
import com.rssfeed.core.base.TIMEOUT_DELAY
import com.rssfeed.core.navigation.NavigationEvent
import com.rssfeed.core.navigation.Navigator
import com.rssfeed.di.APP_NAVIGATOR_QUALIFIER
import com.rssfeed.domain.usecase.ObserveArticles
import com.rssfeed.feature.articles.model.ArticleUiItem
import com.rssfeed.feature.articles.model.ArticlesEvent
import com.rssfeed.feature.articles.model.ArticlesViewState
import com.rssfeed.feature.articles.model.toItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ArticlesViewModel(
  private val observeArticles: ObserveArticles,
) : BaseViewModel<ArticlesEvent>(), KoinComponent {

  private val navigator: Navigator by inject(named(APP_NAVIGATOR_QUALIFIER))

  private val articleItems = MutableStateFlow<List<ArticleUiItem>>(emptyList())
  private val isLoading = MutableStateFlow(true)

  val state = combine(
    articleItems,
    isLoading,
  ) { articleItems, isLoading ->
    ArticlesViewState(
      articleItems = articleItems,
      isLoading = isLoading,
    )
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
    initialValue = ArticlesViewState(),
  )

  override fun onEvent(event: ArticlesEvent) {
    when (event) {
      is ArticlesEvent.ObserveArticles -> handleObservingArticles(event.channelLink)
      ArticlesEvent.OnBackIconClicked -> handleOnBackIconClicked()
      is ArticlesEvent.OnItemClicked -> handleOnItemClicked(event.link)
    }
  }

  private fun handleObservingArticles(channelLink: String) = viewModelScope.launch {
    observeArticles(channelLink)
      .onEach { isLoading.update { false } }
      .collectLatest { articles ->
        articleItems.update { articles.toItems() }
      }
  }

  private fun handleOnBackIconClicked() = viewModelScope.launch {
    navigator.emitDestination(NavigationEvent.NavigateBack)
  }

  private fun handleOnItemClicked(link: String) = viewModelScope.launch {
    navigator.emitDestination(NavigationEvent.Website(link))
  }
}
