package com.radiantmood.kuttit.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CrashlyticsOptInRow(
    modifier: Modifier = Modifier,
    crashlyticsEnabled: Boolean,
    setCrashlyticsEnabled: (Boolean) -> Unit,
) {
    val enabled = if (crashlyticsEnabled) "enabled" else "disabled"
    Row(modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Anonymous crash reporting $enabled"
        )
        Switch(checked = crashlyticsEnabled, onCheckedChange = setCrashlyticsEnabled)
    }
}