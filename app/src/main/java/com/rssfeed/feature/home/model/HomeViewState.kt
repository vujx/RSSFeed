package com.rssfeed.feature.home.model

data class HomeViewState(
  val text: String = "",
  val homeItems: List<HomeItem> = emptyList(),
  val isLoading: Boolean = false,
)
