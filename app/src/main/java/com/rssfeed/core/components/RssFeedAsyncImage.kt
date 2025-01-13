package com.rssfeed.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter.State.Error
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.rssfeed.R

@Composable
fun RssFeedAsyncImage(
  imageUrl: String,
  contentDescription: String,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = FillBounds,
) {
  SubcomposeAsyncImage(
    modifier = modifier,
    contentScale = contentScale,
    model = ImageRequest.Builder(LocalContext.current)
      .data(imageUrl)
      .build(),
    contentDescription = contentDescription,
  ) {
    when (painter.state) {
      is Success -> SubcomposeAsyncImageContent()
      is Error -> Image(
        painter = painterResource(id = R.drawable.ic_rss_feed_fallback_image),
        contentScale = contentScale,
        contentDescription = contentDescription,
      )
      else -> Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.DarkGray),
      )
    }
  }
}
