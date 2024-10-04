package com.rmyhal.containertransform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rmyhal.containertransform.ui.theme.ContainerTransformTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			var showNavBars by remember { mutableStateOf(true) }
			ContainerTransformTheme {
				Scaffold(
					topBar =  {
						TopNavBar(showNavBars)
					},
					bottomBar = {
						BottomNavBar(showNavBars)
					},
					modifier = Modifier.fillMaxSize()) { innerPadding ->
					HomeScreen(
						setShowNavBars = { showNavBars = it },
						modifier = Modifier
							.padding(innerPadding)
					)
				}
			}
		}
	}

	@Composable
	private fun BottomNavBar(showNavBars: Boolean) {
		AnimatedVisibility(
			visible = showNavBars,
			enter = fadeIn() + expandVertically(),
			exit = fadeOut() + shrinkVertically()
		) {
			NavigationBar {
				NavigationBarItem(
					selected = true,
					icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
					label = { Text("Home") },
					onClick = { /* Handle Home click */ }
				)
				NavigationBarItem(
					selected = false,
					icon = { Icon(Icons.Rounded.Settings, contentDescription = "Settings") },
					label = { Text("Settings") },
					onClick = { /* Handle Settings click */ }
				)
				NavigationBarItem(
					selected = false,
					icon = { Icon(Icons.Rounded.Info, contentDescription = "About") },
					label = { Text("About") },
					onClick = { /* Handle About click */ }
				)
			}
		}
	}

	@Composable
	@OptIn(ExperimentalMaterial3Api::class)
	private fun TopNavBar(showNavBars: Boolean) {
		AnimatedVisibility(
			visible = showNavBars,
			enter = fadeIn() + expandVertically(),
			exit = fadeOut() + shrinkVertically()
		) {
			CenterAlignedTopAppBar(title = { Text("Hot takes") })
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
	ContainerTransformTheme {
		HomeScreen(setShowNavBars = {})
	}
}