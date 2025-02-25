package com.rssfeed.core.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
  primary = Color.DarkGray,
  primaryVariant = Color.LightGray,
  secondary = Color.Gray,
  onPrimary = Color.White,
  onSurface = Color.Black,
)

@Composable
fun RSSFeedTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colors = LightColorPalette,
    typography = Typography,
    shapes = Shapes,
    content = content,
  )
}
