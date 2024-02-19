package com.rmyhal.containertransform

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmyhal.containertransform.ui.theme.ContainerTransformTheme
import com.rmyhal.containertransform.ui.theme.Gray10

@Composable
fun AddContentScreen(
	modifier: Modifier = Modifier,
	onBack: () -> Unit,
) {
	BackHandler {
		onBack()
	}
	Column(
		modifier = modifier
			.padding(horizontal = 8.dp)
	) {
		Toolbar(
			onBack = onBack,
		)
		TitleInputField()
		StoryInputField()
	}
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Toolbar(onBack: () -> Unit) {
	CenterAlignedTopAppBar(
		windowInsets = WindowInsets(0, 0, 0, 0),
		title = {},
		actions = {
			FilledTonalButton(
				modifier = Modifier.padding(end = 6.dp),
				onClick = onBack,
			) {
				Text(text = "Save")
			}
		},
		navigationIcon = {
			FilledTonalIconButton(
				colors = IconButtonDefaults.filledTonalIconButtonColors(
					containerColor = MaterialTheme.colorScheme.background
				),
				onClick = onBack,
			) {
				Icon(
					imageVector = Icons.Filled.ArrowBack,
					contentDescription = null,
				)
			}
		}
	)
}

@Composable
private fun TitleInputField() {
	var text by remember { mutableStateOf("") }
	OutlinedTextField(
		modifier = Modifier
			.fillMaxWidth(),
		value = text,
		onValueChange = { text = it },
		placeholder = {
			Text(
				text = "Title",
				color = Gray10,
			)
		},
		colors = OutlinedTextFieldDefaults.colors(
			focusedBorderColor = Color.Transparent,
			unfocusedBorderColor = Color.Transparent,
		)
	)
}

@Composable
private fun StoryInputField() {
	var text by remember { mutableStateOf("") }
	OutlinedTextField(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.imePadding(),
		value = text,
		onValueChange = { text = it },
		placeholder = {
			Text(
				text = "Fragmentation isn't necessarily a curse; it's a testament to Android's diversity...",
				color = Gray10,
			)
		},
		colors = OutlinedTextFieldDefaults.colors(
			focusedBorderColor = Color.Transparent,
			unfocusedBorderColor = Color.Transparent,
		),
	)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	ContainerTransformTheme {
		AddContentScreen {}
	}
}