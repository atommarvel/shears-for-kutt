package com.radiantmood.kuttit.screen.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.repo.SettingsRepo

class OnboardingViewModel : ViewModel() {

    private val _screenModel =
        MutableLiveData<ModelContainer<OnboardingScreenModel>>(LoadingModelContainer())
    val screenModel: LiveData<ModelContainer<OnboardingScreenModel>>
        get() = _screenModel

    init {
        updateScreen()
    }

    private fun updateScreen() {
        _screenModel.value = OnboardingScreenModel(
            apiKey = SettingsRepo.apiKey,
            isCrashlyticsEnabled = SettingsRepo.isCrashlyticsEnabled()
        )
    }

    fun updateApiKey(apiKey: String) {
        SettingsRepo.apiKey = apiKey
        updateScreen()
    }

    fun updateCrashlytics(enabled: Boolean) {
        SettingsRepo.setIsCrashlyticsEnabled(enabled)
        updateScreen()
    }
}