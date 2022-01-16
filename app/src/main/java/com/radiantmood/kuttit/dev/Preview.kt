package com.radiantmood.kuttit.dev

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Container for displaying previews more distinctly.
 */
@Composable
fun PreviewBox(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .padding(8.dp),
        content = content,
    )
}