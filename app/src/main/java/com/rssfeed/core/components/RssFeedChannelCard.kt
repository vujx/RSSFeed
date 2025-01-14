package com.rssfeed.core.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rssfeed.R
import com.rssfeed.core.base.ChannelUiItem

@Composable
fun RssFeedChannelCard(
  item: ChannelUiItem,
  onCardClick: () -> Unit,
  onFavoriteIconClicked: () -> Unit,
  onSubscribedIconClicked: () -> Unit,
  modifier: Modifier = Modifier,
  onDeleteIconClicked: (() -> Unit)? = null,
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable(onClick = onCardClick),
    shape = MaterialTheme.shapes.small,
    elevation = 4.dp,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        RssFeedAsyncImage(
          imageUrl = item.imageUrl,
          contentDescription = item.title,
          modifier = Modifier
            .size(60.dp)
            .clip(MaterialTheme.shapes.small),
        )
        Column(
          modifier = Modifier.padding(horizontal = 8.dp),
        ) {
          Text(
            text = item.title,
            style = MaterialTheme.typography.h1,
          )
          Text(
            text = item.description,
            style = MaterialTheme.typography.body1,
          )
          ChannelIcons(
            isFavorite = item.isFavorite,
            isSubscribed = item.isSubscribed,
            onDeleteIconClicked = onDeleteIconClicked,
            onFavoriteIconClicked = onFavoriteIconClicked,
            onSubscribedIconClicked = onSubscribedIconClicked,
          )
        }
      }
    }
  }
}

@Composable
private fun ChannelIcons(
  isFavorite: Boolean,
  isSubscribed: Boolean,
  onFavoriteIconClicked: () -> Unit,
  onSubscribedIconClicked: () -> Unit,
  onDeleteIconClicked: (() -> Unit)?,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround,
  ) {
    AnimatedContent(
      targetState = isFavorite,
      label = "favoriteIcon",
    ) { isFavorite ->
      if (isFavorite) {
        ChannelIcon(
          imageVector = Icons.Default.Favorite,
          contentDescription = stringResource(id = R.string.home_screen_favorite_icon_content_description),
          modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onFavoriteIconClicked),
        )
      } else {
        ChannelIcon(
          imageVector = Icons.Default.FavoriteBorder,
          contentDescription = stringResource(id = R.string.home_screen_favorite_icon_content_description),
          modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onFavoriteIconClicked),
        )
      }
    }
    AnimatedContent(
      targetState = isSubscribed,
      label = "subscribedIcon",
    ) { isSubscribed ->
      if (isSubscribed) {
        ChannelIcon(
          imageVector = Icons.Default.Notifications,
          contentDescription = stringResource(id = R.string.home_screen_notification_icon_content_description),
          modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onSubscribedIconClicked),
        )
      } else {
        ChannelIcon(
          imageVector = Icons.Default.NotificationsNone,
          contentDescription = stringResource(id = R.string.home_screen_notification_icon_content_description),
          modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onSubscribedIconClicked),
        )
      }
    }
    if (onDeleteIconClicked != null) {
      ChannelIcon(
        imageVector = Icons.Default.DeleteOutline,
        contentDescription = stringResource(id = R.string.home_screen_delete_icon_content_description),
        modifier = Modifier
          .clip(CircleShape)
          .clickable(onClick = onDeleteIconClicked),
      )
    }
  }
}

@Composable
private fun ChannelIcon(
  imageVector: ImageVector,
  contentDescription: String,
  modifier: Modifier = Modifier,
) {
  Icon(
    imageVector = imageVector,
    contentDescription = contentDescription,
    modifier = modifier
      .padding(8.dp)
      .size(18.dp),
  )
}
