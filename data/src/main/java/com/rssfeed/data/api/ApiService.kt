package com.rssfeed.data.api

interface ApiService {

  suspend fun addRssFeed(url: String)
}
