package com.rmyhal.containertransform

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmyhal.containertransform.ui.theme.ContainerTransformTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
	Box(
		modifier = modifier
			.fillMaxSize(),
	) {
		HotContent()
		FabContainer(
			modifier = Modifier
				.align(Alignment.BottomEnd)
		)
	}
}

@Composable
private fun FabContainer(
	modifier: Modifier = Modifier,
) {
	var containerState by remember { mutableStateOf(ContainerState.Fab) }
	val transition = updateTransition(containerState, label = "container transform")
	val animatedColor by transition.animateColor(
		label = "color",
	) { state ->
		when (state) {
			ContainerState.Fab -> MaterialTheme.colorScheme.primaryContainer
			ContainerState.Fullscreen -> MaterialTheme.colorScheme.surface
		}
	}

	val cornerRadius by transition.animateDp(
		label = "corner radius",
		transitionSpec = {
			when (targetState) {
				ContainerState.Fab -> tween(
					durationMillis = 400,
					easing = EaseOutCubic,
				)

				ContainerState.Fullscreen -> tween(
					durationMillis = 200,
					easing = EaseInCubic,
				)
			}
		}
	) { state ->
		when (state) {
			ContainerState.Fab -> 22.dp
			ContainerState.Fullscreen -> 0.dp
		}
	}
	val elevation by transition.animateDp(
		label = "elevation",
		transitionSpec = {
			when (targetState) {
				ContainerState.Fab -> tween(
					durationMillis = 400,
					easing = EaseOutCubic,
				)

				ContainerState.Fullscreen -> tween(
					durationMillis = 200,
					easing = EaseOutCubic,
				)
			}
		}
	) { state ->
		when (state) {
			ContainerState.Fab -> 6.dp
			ContainerState.Fullscreen -> 0.dp
		}
	}
	val padding by transition.animateDp(
		label = "padding",
	) { state ->
		when (state) {
			ContainerState.Fab -> 16.dp
			ContainerState.Fullscreen -> 0.dp
		}
	}

	transition.AnimatedContent(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.padding(end = padding, bottom = padding)
			.shadow(
				elevation = elevation,
				shape = RoundedCornerShape(cornerRadius)
			)
			.drawBehind { drawRect(animatedColor) },
		transitionSpec = {
			(
				fadeIn(animationSpec = tween(220, delayMillis = 90)) +
					scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90))
				)
				.togetherWith(fadeOut(animationSpec = tween(90)))
				.using(SizeTransform(clip = false, sizeAnimationSpec = { _, _ ->
					tween(
						durationMillis = 500,
						easing = FastOutSlowInEasing
					)
				}))
		}
	) { state ->
		when (state) {
			ContainerState.Fab -> {
				Fab(
					modifier = Modifier,
					onClick = { containerState = ContainerState.Fullscreen }
				)
			}

			ContainerState.Fullscreen -> {
				AddContentScreen(
					modifier = Modifier,
					onBack = { containerState = ContainerState.Fab }
				)
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
		HotTakes()
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
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	Box(
		modifier = modifier
			.defaultMinSize(
				minWidth = 76.dp,
				minHeight = 76.dp,
			)
			.clickable(
				onClick = onClick,
			),
		contentAlignment = Alignment.Center,
	) {
		Icon(
			painter = rememberVectorPainter(Icons.Filled.Add),
			contentDescription = null,
		)
	}
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