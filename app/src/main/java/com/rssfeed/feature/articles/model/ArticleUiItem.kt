package com.rssfeed.feature.articles.model

import androidx.core.text.HtmlCompat
import com.rssfeed.domain.model.ArticleItem

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
      description = description.getTextFromHtml().trim(),
      imageUrl = imageUrl,
      link = link,
      pubDate = pubDate,
    )
  }
}

private fun String.getTextFromHtml(): String {
  return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    .toString()
    .replace('\n', ' ')
    .replace('\u00A0', ' ') // Non-breaking space
    .replace('\uFFFC', ' ') // Object replacement character
    .trim()
}
