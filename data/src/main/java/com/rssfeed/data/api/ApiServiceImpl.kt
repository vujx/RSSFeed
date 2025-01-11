package com.rssfeed.data.api

import com.rssfeed.data.api.model.Feed
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML

class ApiServiceImpl(
  private val client: HttpClient,
  private val xml: XML,
) : ApiService {

  override suspend fun addFeed(url: String) {
    val response = client
      .request(url)
      .bodyAsText()

    // Get Feed
    xml.decodeFromString<Feed>(response)
  }
}
