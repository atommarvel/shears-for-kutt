package com.radiantmood.kuttit.screen.onboarding

import com.radiantmood.kuttit.data.local.FinishedModelContainer

data class OnboardingScreenModel(
    val apiKey: String? = null,
    val baseUrl: String,
    val isCrashlyticsEnabled: Boolean,
) : FinishedModelContainer<OnboardingScreenModel>()
