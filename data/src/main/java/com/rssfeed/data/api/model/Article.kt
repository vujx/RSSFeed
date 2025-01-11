package com.rssfeed.data.api.model

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("item")
data class Article(
  @XmlElement(true)
  val title: String?,
  @XmlElement(true)
  val description: String?,
  @XmlElement(true)
  val link: String?,
  @XmlElement(true)
  val pubDate: String?,
  @XmlElement(true)
  val media: MediaThumbnail?,
)

@Serializable
@XmlSerialName("thumbnail", namespace = MEDIA_NAMESPACE, prefix = "media:")
data class MediaThumbnail(
  val url: String?,
)

private const val MEDIA_NAMESPACE = "http://search.yahoo.com/mrss/"
