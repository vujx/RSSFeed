package com.rssfeed.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.rssfeed.R
import com.rssfeed.core.components.RssFeedFadeInOutContent
import com.rssfeed.feature.home.model.HomeEvent

@Composable
fun SearchTopBar(
  searchText: () -> String,
  onEvent: (HomeEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  TopAppBar(
    modifier = modifier,
    backgroundColor = MaterialTheme.colors.primary,
  ) {
    SearchTextField(
      searchText = searchText(),
      onEvent = onEvent,
    )
  }
}

@Composable
private fun SearchTextField(
  searchText: String,
  keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
  onEvent: (HomeEvent) -> Unit,
) {
  BasicTextField(
    modifier = Modifier
      .padding(horizontal = 8.dp)
      .fillMaxSize(),
    value = searchText,
    onValueChange = {
      onEvent(HomeEvent.OnSearchUpdated(it))
    },
    textStyle = MaterialTheme.typography.body1.copy(
      color = MaterialTheme.colors.onPrimary,
    ),
    cursorBrush = SolidColor(MaterialTheme.colors.onPrimary),
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(
      onSearch = {
        onEvent(HomeEvent.OnSearchButtonClick)
        keyboardController?.hide()
      },
    ),
    decorationBox = { innerTextField ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        RssFeedFadeInOutContent(
          condition = searchText.isEmpty(),
          content = {
            SearchIcon()
          },
        )
        SearchText(
          searchText = searchText,
          innerTextField = {
            innerTextField()
          },
          modifier = Modifier.weight(1f),
        )
        RssFeedFadeInOutContent(
          condition = searchText.isNotEmpty(),
          content = {
            SearchClearIcon {
              onEvent(HomeEvent.OnSearchUpdated(""))
            }
          },
        )
      }
    },
  )
}

@Composable
private fun SearchText(
  searchText: String,
  innerTextField: @Composable () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier,
  ) {
    RssFeedFadeInOutContent(
      condition = searchText.isEmpty(),
      content = {
        Text(
          text = stringResource(id = R.string.home_screen_search_field_hint),
          style = MaterialTheme.typography.body1,
          color = MaterialTheme.colors.onPrimary,
        )
      },
    )
    RssFeedFadeInOutContent(
      condition = searchText.isNotEmpty(),
      content = {
        innerTextField()
      },
    )
  }
}

@Composable
private fun SearchIcon(
  contentDescription: String? = stringResource(id = R.string.home_screen_search_icon_content_description),
) {
  Icon(
    imageVector = Icons.Default.Search,
    contentDescription = contentDescription,
    tint = MaterialTheme.colors.onPrimary,
    modifier = Modifier
      .padding(8.dp)
      .size(24.dp),
  )
}

@Composable
private fun SearchClearIcon(
  contentDescription: String? = stringResource(id = R.string.home_screen_clear_icon_content_description),
  onClearIconClicked: () -> Unit,
) {
  Box(
    modifier = Modifier
      .clip(CircleShape)
      .clickable {
        onClearIconClicked()
      }
      .padding(8.dp)
      .clip(CircleShape)
      .background(MaterialTheme.colors.onPrimary)
      .size(20.dp),
  ) {
    Icon(
      imageVector = Icons.Default.Clear,
      contentDescription = contentDescription,
      tint = MaterialTheme.colors.primary,
      modifier = Modifier
        .align(Alignment.Center)
        .padding(2.dp),
    )
  }
}
