package com.radiantmood.kuttit.repo

import android.content.SharedPreferences
import com.radiantmood.kuttit.util.getPrefDelegate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KuttUrlProvider @Inject constructor(prefs: SharedPreferences) {
    var baseUrl: String by getPrefDelegate(prefs, "base_url", "https://kutt.it")
    val apiBaseUrl: String get() = "$baseUrl/api/v2/"
}