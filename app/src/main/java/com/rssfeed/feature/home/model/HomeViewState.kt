package com.rssfeed.feature.home.model

import com.rssfeed.core.base.ChannelUiItem

data class HomeViewState(
  val text: String = "",
  val homeItems: List<ChannelUiItem> = emptyList(),
  val isLoading: Boolean = false,
)
