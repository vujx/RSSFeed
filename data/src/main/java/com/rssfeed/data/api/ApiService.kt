package com.rssfeed.data.api

interface ApiService {

  suspend fun addFeed(url: String)
}
