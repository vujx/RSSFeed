package com.rssfeed.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RssFeedEmptyListScreen(
  title: String,
  description: String,
  contentDescription: String,
  modifier: Modifier = Modifier,
  imageVector: ImageVector = Icons.Default.Search,
) {
  Column(
    modifier = modifier.padding(horizontal = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Icon(
      imageVector = imageVector,
      contentDescription = contentDescription,
      tint = MaterialTheme.colors.secondary,
      modifier = Modifier.size(56.dp),
    )
    Spacer(modifier = Modifier.height(40.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.h3,
      color = MaterialTheme.colors.secondary,
      textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
      text = description,
      style = MaterialTheme.typography.body1,
      color = MaterialTheme.colors.secondary,
      textAlign = TextAlign.Center,
    )
  }
}
