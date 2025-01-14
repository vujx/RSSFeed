package com.rssfeed.feature.articles.model

import com.rssfeed.domain.model.ArticleItem
import java.text.SimpleDateFormat
import java.util.Locale

data class ArticleUiItem(
  val title: String,
  val description: String,
  val imageUrl: String,
  val link: String,
  val pubDate: String,
)

fun List<ArticleItem>.toItems() = map { articleItem ->
  with(articleItem) {
    ArticleUiItem(
      title = title.trim(),
      description = description.trim(),
      imageUrl = imageUrl,
      link = link,
      pubDate = formatPubDate(pubDate),
    )
  }
}

private fun formatPubDate(dateString: String): String {
  val inputFormat = SimpleDateFormat(INPUT_DATE_PATTERN, Locale.ENGLISH)
  val outputFormat = SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.ENGLISH)

  return inputFormat.parse(dateString)?.let { outputFormat.format(it) }.orEmpty()
}

private const val INPUT_DATE_PATTERN = "yyyy MM dd, HH:mm:ss"
private const val OUTPUT_DATE_PATTERN = "EEE, dd MMM yyyy"
