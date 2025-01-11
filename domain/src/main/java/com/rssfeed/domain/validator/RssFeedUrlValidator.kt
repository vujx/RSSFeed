package com.rssfeed.domain.validator

class RssFeedUrlValidator {

  private companion object {
    const val HTTP_PREFIX = "http://"
    const val HTTPS_PREFIX = "https://"
    val DEFAULT_RSS_INDICATORS = listOf(".rss", ".xml", "/feed", "/rss", "?rss", "?feed")
  }

  operator fun invoke(url: String): Boolean {
    val hasValidProtocol = url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)
    val hasRssIndicator = DEFAULT_RSS_INDICATORS.any { url.contains(it) }

    return hasValidProtocol && hasRssIndicator
  }
}
