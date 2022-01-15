package com.radiantmood.kuttit.screen.onboarding

import com.radiantmood.kuttit.data.FinishedModelContainer

data class OnboardingScreenModel(
    val apiKey: String? = null,
    val baseUrl: String,
    val isCrashlyticsEnabled: Boolean,
) : FinishedModelContainer<OnboardingScreenModel>()
