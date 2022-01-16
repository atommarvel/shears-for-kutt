package com.radiantmood.kuttit.repo

import android.content.SharedPreferences
import com.radiantmood.kuttit.util.getPrefDelegate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Tracks if the user has experienced the onboarding process yet.
 */
@Singleton
class OnboardingStatusSource @Inject constructor(prefs: SharedPreferences) {
    var onboardingFinished: Boolean by getPrefDelegate(prefs, "onboarding_finished", false)
}