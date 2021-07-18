package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.repo.SettingsRepo
import com.radiantmood.kuttit.ui.component.ApiKeyInput
import com.radiantmood.kuttit.ui.component.UrlClickableText

@Composable
fun ApiKeyOnboarding(
    screenModel: OnboardingScreenModel,
    baseUrl: String = SettingsRepo.baseUrl.orEmpty(),
    setApiKey: (String) -> Unit,
) {
    Surface {
        Column {
            ExplanationText(baseUrl)
            Spacer(Modifier.height(8.dp))
            ApiKeyInput(
                apiKey = screenModel.apiKey,
                setApiKey = setApiKey
            )
        }
    }
}

@Composable
private fun ExplanationText(baseUrl: String = SettingsRepo.baseUrl.orEmpty()) {
    UrlClickableText(
        text = buildAnnotatedString {
            append(stringResource(R.string.api_explanation, baseUrl))
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    Column {
        ApiKeyOnboarding(
            screenModel = OnboardingScreenModel(
                apiKey = "abcdefghijklmnopqrstuvwxyz",
                isCrashlyticsEnabled = true
            ),
            baseUrl = "https://kutt.it",
            setApiKey = {}
        )
    }
}