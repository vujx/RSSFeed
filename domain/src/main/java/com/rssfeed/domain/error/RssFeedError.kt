package com.rssfeed.domain.error

sealed interface RssFeedError {
  data object ConnectivityError : RssFeedError
  data object NotFoundError : RssFeedError
  data object UnknownError : RssFeedError
}
