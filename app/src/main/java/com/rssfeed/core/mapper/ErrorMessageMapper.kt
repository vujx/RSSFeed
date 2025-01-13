package com.rssfeed.core.mapper

import com.rssfeed.R
import com.rssfeed.core.dictionary.Dictionary
import com.rssfeed.domain.error.RssFeedError

class ErrorMessageMapper(
  private val dictionary: Dictionary,
) {

  operator fun invoke(error: RssFeedError) = when (error) {
    RssFeedError.ConnectivityError -> dictionary.getString(R.string.connectivity_error_message)
    RssFeedError.NotFoundError -> dictionary.getString(R.string.not_found_error_message)
    RssFeedError.UnknownError -> dictionary.getString(R.string.unknown_error_message)
  }
}
