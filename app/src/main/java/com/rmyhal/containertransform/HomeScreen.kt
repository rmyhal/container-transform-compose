@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.rmyhal.containertransform

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmyhal.containertransform.ui.theme.ContainerTransformTheme
import kotlinx.coroutines.delay
import me.rmyhal.contentment.Contentment

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize(),
  ) {
    HotContent()
    FabContainer(
      modifier = Modifier
        .align(Alignment.BottomEnd),
    )
  }
}

@Composable
private fun FabContainer(
  modifier: Modifier = Modifier,
) {
  var containerState by remember { mutableStateOf(ContainerState.Fab) }

  SharedTransitionLayout(
    modifier = modifier,
  ) {
    AnimatedContent(
      targetState = containerState,
      contentAlignment = Alignment.Center,
    ) { state ->
      when (state) {
        ContainerState.Fab -> {
          Fab(
            onClick = { containerState = ContainerState.Fullscreen },
            animatedVisibilityScope = this@AnimatedContent,
            sharedTransitionScope = this@SharedTransitionLayout,
          )
        }

        ContainerState.Fullscreen -> {
          AddContentScreen(
            modifier = Modifier,
            onBack = { containerState = ContainerState.Fab },
            animatedVisibilityScope = this@AnimatedContent,
            sharedTransitionScope = this@SharedTransitionLayout,
          )
        }
      }
    }
  }
}

@Composable
private fun HotContent() {
  Column {
    SearchBar(
      modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 12.dp, vertical = 6.dp)
    )

    var uiState by remember { mutableStateOf<UiState>(UiState.Loading) }
    LaunchedEffect(Unit) {
      // fake loading
      delay(600)
      uiState = UiState.Loaded(hotTakes)
    }
    Box(
      modifier = Modifier.fillMaxSize(),
    ) {
      Contentment {
        when (uiState) {
          is UiState.Loading -> indicator {
            CircularProgressIndicator(
              modifier = Modifier.align(Alignment.Center)
            )
          }

          is UiState.Loaded -> content { HotTakes() }
        }
      }
    }
  }
}

@Composable
private fun SearchBar(modifier: Modifier = Modifier) {
  OutlinedTextField(
    modifier = modifier,
    value = "",
    onValueChange = {},
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Menu,
        contentDescription = null,
      )
    },
    placeholder = {
      Text(
        text = "Search your hot takes"
      )
    },
    colors = OutlinedTextFieldDefaults.colors(
      focusedBorderColor = Color.Transparent,
      unfocusedBorderColor = Color.Transparent,
      unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
      focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
    ),
    shape = RoundedCornerShape(50)
  )
}

@Composable
private fun HotTakes() {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(hotTakes) { HotTake(hotTake = it) }
  }
}

@Composable
private fun HotTake(hotTake: HotTake) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    onClick = { },
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      hotTake.title?.let {
        Text(
          text = hotTake.title,
          style = MaterialTheme.typography.titleMedium,
        )
      }
      Text(
        modifier = Modifier
          .padding(top = 6.dp),
        maxLines = 3,
        text = hotTake.take,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Composable
private fun Fab(
  onClick: () -> Unit,
  sharedTransitionScope: SharedTransitionScope,
  animatedVisibilityScope: AnimatedVisibilityScope,
) {
  with(sharedTransitionScope) {
    FloatingActionButton(
      modifier = Modifier
        .sharedBounds(
          sharedContentState = rememberSharedContentState(key = "bounds"),
          animatedVisibilityScope = animatedVisibilityScope,
          enter = fadeIn(animationSpec = tween(220, easing = FastOutSlowInEasing)),
          exit = fadeOut(animationSpec = tween(220, easing = FastOutSlowInEasing)),
          resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
        )
        .padding(16.dp)
        .defaultMinSize(76.dp, 76.dp),
      onClick = onClick,
    ) {
      Icon(Icons.Filled.Add, "Floating action button")
    }
  }
}

sealed interface UiState {
  data class Loaded(val list: List<HotTake>) : UiState
  data object Loading : UiState
}

enum class ContainerState {
  Fab,
  Fullscreen,
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
  ContainerTransformTheme {
    HomeScreen()
  }
}