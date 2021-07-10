package com.radiantmood.kuttit.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlatformDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = true),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = content
    )
}