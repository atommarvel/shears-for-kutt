package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ApiKeyOnboarding(apiKey: String?) {
    Column(Modifier.fillMaxWidth()) {
        Text("apiKey:")
        Text(text = apiKey.orEmpty())
    }
}