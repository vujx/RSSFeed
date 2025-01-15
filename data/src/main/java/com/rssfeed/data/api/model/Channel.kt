package com.rssfeed.data.api.model

import com.rssfeed.data.schema.ChannelEntity
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("channel")
data class Channel(
  @XmlElement(true)
  val title: String?,
  @XmlElement(true)
  val description: String?,
  @XmlElement(true)
  val link: String?,
  @XmlElement(true)
  val image: Image?,
  @XmlElement(true)
  val lastBuildDate: String?,
  @XmlElement(true)
  val articles: List<Article>?,
) {

  fun toChannelEntity(
    rssFeedUrl: String,
    isFavorite: Long = 0L,
    isSubscribed: Long = 0L,
  ): ChannelEntity? {
    if (link.isNullOrBlank()) return null

    return ChannelEntity(
      link = link,
      title = title.orEmpty(),
      description = description.orEmpty(),
      imageUrl = image?.url.orEmpty(),
      lastBuildDate = lastBuildDate.orEmpty(),
      rssFeedUrl = rssFeedUrl,
      isFavorite = isFavorite,
      isSubscribed = isSubscribed,
    )
  }
}

@Serializable
@XmlSerialName("image")
data class Image(
  @XmlElement(true)
  val url: String?,
)
