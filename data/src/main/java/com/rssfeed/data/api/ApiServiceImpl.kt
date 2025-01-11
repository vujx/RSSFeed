package com.rssfeed.data.api

import com.rssfeed.data.api.model.RssFeed
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML

class ApiServiceImpl(
  private val client: HttpClient,
  private val xml: XML,
) : ApiService {

  override suspend fun addRssFeed(url: String): RssFeed {
    val response = client
      .request(url) {
        header(HttpHeaders.Accept, "application/xml")
        contentType(ContentType.Application.Xml)
      }
      .bodyAsText()

    return xml.decodeFromString<RssFeed>(response)
  }
}
