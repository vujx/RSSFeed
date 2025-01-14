package com.rssfeed.domain.validator

class RssFeedUrlValidator {

  private companion object {
    const val HTTP_PREFIX = "http://"
    const val HTTPS_PREFIX = "https://"
  }

  operator fun invoke(url: String): Boolean =
    url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)
}
