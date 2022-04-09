package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.util.Fullscreen

@Composable
fun ApiKeyMissing(navToSettings: () -> Unit) {
    Fullscreen {
        Text(
            text = stringResource(R.string.missing_api_msg),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navToSettings() }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            Text("Settings")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ApiKeyMissing {}
}