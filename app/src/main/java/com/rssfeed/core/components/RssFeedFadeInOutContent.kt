package com.rssfeed.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RssFeedFadeInOutContent(
  condition: Boolean,
  content: @Composable () -> Unit,
  modifier: Modifier = Modifier,
) {
  AnimatedVisibility(
    modifier = modifier,
    visible = condition,
    enter = fadeIn(),
    exit = fadeOut(),
  ) {
    content()
  }
}
