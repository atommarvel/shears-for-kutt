package com.radiantmood.kuttit.repo

import com.radiantmood.kuttit.dev.getApiKeyOrEmpty
import com.radiantmood.kuttit.util.getPrefDelegate

object ApiKeyRepo {
    var apiKey: String? by getPrefDelegate(prefs, "api_key", getApiKeyOrEmpty())
}