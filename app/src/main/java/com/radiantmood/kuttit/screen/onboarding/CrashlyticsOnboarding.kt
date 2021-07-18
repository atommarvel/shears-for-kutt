package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.dev.PreviewBox
import com.radiantmood.kuttit.ui.component.CrashlyticsOptInRow

@Composable
fun CrashlyticsOnboarding(
    screenModel: OnboardingScreenModel,
    setCrashlyticsEnabled: (Boolean) -> Unit,
) {
    Card {
        Column(Modifier.padding(8.dp)) {
            Text("Enable anonymous crash metrics? This helps the app developer fix crashes and errors faster. You can enable/disable in Settings as well.")
            Spacer(Modifier.height(16.dp))
            CrashlyticsOptInRow(
                crashlyticsEnabled = screenModel.isCrashlyticsEnabled,
                setCrashlyticsEnabled = setCrashlyticsEnabled
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    PreviewBox {
        CrashlyticsOnboarding(
            screenModel = OnboardingScreenModel(
                apiKey = "abcdefghijklmnopqrstuvwxyz",
                isCrashlyticsEnabled = true
            ),
            setCrashlyticsEnabled = {}
        )
    }
}