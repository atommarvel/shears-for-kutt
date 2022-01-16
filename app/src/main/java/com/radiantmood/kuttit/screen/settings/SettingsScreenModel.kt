package com.radiantmood.kuttit.screen.settings

import com.radiantmood.kuttit.data.local.FinishedModelContainer

data class SettingsScreenModel(
    val apiKey: String?,
    val isCrashlyticsEnabled: Boolean,
) : FinishedModelContainer<SettingsScreenModel>()