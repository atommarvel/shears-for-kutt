package com.radiantmood.kuttit.repo

import android.content.SharedPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.radiantmood.kuttit.util.getPrefDelegate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Tracks if crashlytics is enabled and passes on the state to [FirebaseCrashlytics].
 */
@Singleton
class CrashlyticsStatusSource @Inject constructor(prefs: SharedPreferences) {
    private var crashlyticsEnabled: Boolean by getPrefDelegate(prefs, "crashlytics_enabled", false)

    fun isCrashlyticsEnabled(): Boolean = crashlyticsEnabled

    fun setIsCrashlyticsEnabled(enabled: Boolean) {
        crashlyticsEnabled = enabled
        // TODO: sanity check, does this need to happen on app start too? document answer!
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(enabled)
    }
}