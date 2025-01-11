package com.rssfeed.data.api

import com.rssfeed.data.api.model.RssFeed

interface ApiService {

  suspend fun addRssFeed(url: String): RssFeed
}
