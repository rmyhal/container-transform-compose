package com.rmyhal.containertransform

import androidx.compose.runtime.Stable

@Stable
data class HotTake(
	val title: String?,
	val take: String,
)

val hotTakes = listOf(
	HotTake(
		title = null,
		take = "Artificial Intelligence isn't just a threat to jobs; it's a catalyst for redefining the workforce. Instead of fearing automation, we should focus on reskilling and upskilling workers to thrive in a future augmented by AI.",
	),
	HotTake(
		title = "Security?",
		take = "Prioritizing user security over convenience should be non-negotiable in Android app development. While features like single sign-on and biometric authentication enhance user experience, they should not compromise security standards. Developers must strike a balance between user convenience and robust security measures to safeguard user data and trust."
	),
	HotTake(
		title = "Compose or Collapse",
		take = "Jetpack Compose isn't just a toolkit; it's the litmus test for Android's future relevance. Embrace Compose's radical departure from XML layouts or risk irrelevance in a rapidly evolving mobile ecosystem.",
	)
)
