package com.radiantmood.kuttit.repo

import android.content.SharedPreferences
import com.radiantmood.kuttit.dev.getApiKeyOrEmpty
import com.radiantmood.kuttit.util.getPrefDelegate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Tracks the user provided api key required to interact with Kutt.
 */
@Singleton
class KuttApiKeySource @Inject constructor(prefs: SharedPreferences) {
    var apiKey: String by getPrefDelegate(prefs, "api_key", getApiKeyOrEmpty())
}