package com.rssfeed.feature.home.model

import com.rssfeed.core.base.BaseChannelItem

data class HomeViewState(
  val text: String = "",
  val homeItems: List<BaseChannelItem> = emptyList(),
  val isLoading: Boolean = false,
)
