package com.radiantmood.kuttit.screen.settings

import com.radiantmood.kuttit.data.FinishedModelContainer

data class SettingsScreenModel(
    val apiKey: String?,
    val isCrashlyticsEnabled: Boolean,
) : FinishedModelContainer<SettingsScreenModel>()