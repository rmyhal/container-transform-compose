package com.rmyhal.containertransform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rmyhal.containertransform.ui.theme.ContainerTransformTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			ContainerTransformTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					HomeScreen(
						modifier = Modifier
							.padding(innerPadding)
					)
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	ContainerTransformTheme {
		HomeScreen()
	}
}