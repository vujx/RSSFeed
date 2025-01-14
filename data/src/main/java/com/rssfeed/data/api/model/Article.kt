package com.rssfeed.data.api.model

import com.rssfeed.data.schema.ArticleEntity
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.text.SimpleDateFormat
import java.util.Locale

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
) {

  fun toArticleEntity(channelLink: String): ArticleEntity? {
    if (link.isNullOrBlank()) return null

    return ArticleEntity(
      title = title.orEmpty(),
      description = description.orEmpty(),
      link = link,
      imageUrl = media?.url.orEmpty(),
      pubDate = formatPubDate(pubDate.orEmpty()),
      channelLink = channelLink,
    )
  }
}

@Serializable
@XmlSerialName("thumbnail", namespace = MEDIA_NAMESPACE, prefix = "media:")
data class MediaThumbnail(
  val url: String?,
)

private fun formatPubDate(dateString: String): String {
  val inputFormat = SimpleDateFormat(INPUT_DATE_PATTERN, Locale.ENGLISH)
  val outputFormat = SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.ENGLISH)

  return runCatching {
    inputFormat.parse(dateString)?.let { outputFormat.format(it) }
  }.getOrNull().orEmpty()
}

private const val MEDIA_NAMESPACE = "http://search.yahoo.com/mrss/"
private const val INPUT_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z"
private const val OUTPUT_DATE_PATTERN = "yyyy MM dd, HH:mm:ss"
