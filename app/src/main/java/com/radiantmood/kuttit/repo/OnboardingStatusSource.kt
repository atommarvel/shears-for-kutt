package com.radiantmood.kuttit.repo

import android.content.SharedPreferences
import com.radiantmood.kuttit.util.getPrefDelegate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingStatusSource @Inject constructor(prefs: SharedPreferences) {
    var onboardingFinished: Boolean by getPrefDelegate(prefs, "onboarding_finished", false)
}