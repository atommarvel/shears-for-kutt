package com.radiantmood.kuttit.screen.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.repo.CrashlyticsStatusSource
import com.radiantmood.kuttit.repo.KuttApiKeySource
import com.radiantmood.kuttit.repo.KuttUrlProvider
import com.radiantmood.kuttit.repo.OnboardingStatusSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val apiKeySource: KuttApiKeySource,
    private val crashlyticsStatusSource: CrashlyticsStatusSource,
    private val kuttUrlProvider: KuttUrlProvider,
    private val onboardingStatusSource: OnboardingStatusSource,
) : ViewModel() {

    private val _screenModel =
        MutableLiveData<ModelContainer<OnboardingScreenModel>>(LoadingModelContainer())
    val screenModel: LiveData<ModelContainer<OnboardingScreenModel>>
        get() = _screenModel

    init {
        updateScreen()
    }

    private fun updateScreen() {
        _screenModel.value = OnboardingScreenModel(
            apiKey = apiKeySource.apiKey,
            baseUrl = kuttUrlProvider.baseUrl,
            isCrashlyticsEnabled = crashlyticsStatusSource.isCrashlyticsEnabled(),
        )
    }

    fun updateApiKey(apiKey: String) {
        apiKeySource.apiKey = apiKey
        updateScreen()
    }

    fun updateCrashlytics(enabled: Boolean) {
        crashlyticsStatusSource.setIsCrashlyticsEnabled(enabled)
        updateScreen()
    }

    fun setOnboardingFinished(isFinished: Boolean) {
        onboardingStatusSource.onboardingFinished = isFinished
    }
}