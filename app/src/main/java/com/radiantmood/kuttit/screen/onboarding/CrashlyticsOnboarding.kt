package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.ui.component.CrashlyticsOptInRow

@Composable
fun CrashlyticsOnboarding(screenModel: OnboardingScreenModel) {
    val vm = LocalOnboardingViewModel.current // TODO: nah
    Column {
        Text("Enable anonymous crash metrics? This helps the app developer fix crashes and errors faster. You can enable/disable in Settings as well.")
        Spacer(Modifier.height(16.dp))
        CrashlyticsOptInRow(
            crashlyticsEnabled = screenModel.isCrashlyticsEnabled,
            setCrashlyticsEnabled = vm::updateCrashlytics
        )
    }
}