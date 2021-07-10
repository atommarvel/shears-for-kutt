package com.radiantmood.kuttit.repo

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.radiantmood.kuttit.dev.getApiKeyOrEmpty
import com.radiantmood.kuttit.util.getPrefDelegate

object SettingsRepo {
    var baseUrl: String? by getPrefDelegate(prefs, "base_url", "kutt.it")
    var apiKey: String? by getPrefDelegate(prefs, "api_key", getApiKeyOrEmpty())

    private var crashlyticsEnabled: Boolean? by getPrefDelegate(prefs, "crashlytics_enabled", false)

    fun isCrashlyticsEnabled(): Boolean = crashlyticsEnabled ?: false

    fun setIsCrashlyticsEnabled(enabled: Boolean) {
        crashlyticsEnabled = enabled
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(enabled)
    }
}