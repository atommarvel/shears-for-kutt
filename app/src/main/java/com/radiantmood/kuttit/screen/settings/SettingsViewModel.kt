package com.radiantmood.kuttit.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.repo.SettingsRepo

class SettingsViewModel : ViewModel() {

    private val _settingsScreen =
        MutableLiveData<ModelContainer<SettingsScreenModel>>(LoadingModelContainer())
    val settingsScreen: LiveData<ModelContainer<SettingsScreenModel>>
        get() = _settingsScreen

    init {
        updateScreen()
    }

    private fun updateScreen() {
        _settingsScreen.value = SettingsScreenModel(
            apiKey = SettingsRepo.apiKey,
            isCrashlyticsEnabled = SettingsRepo.isCrashlyticsEnabled()
        )
    }

    fun updateCrashlytics(enabled: Boolean) {
        SettingsRepo.setIsCrashlyticsEnabled(enabled)
        updateScreen()
    }

    fun updateApiKey(key: String) {
        SettingsRepo.apiKey = key
        updateScreen()
    }
}