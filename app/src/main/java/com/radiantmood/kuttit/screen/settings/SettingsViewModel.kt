package com.radiantmood.kuttit.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.data.local.LoadingModelContainer
import com.radiantmood.kuttit.data.local.ModelContainer
import com.radiantmood.kuttit.repo.CrashlyticsStatusSource
import com.radiantmood.kuttit.repo.KuttApiKeySource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val apiKeySource: KuttApiKeySource,
    private val crashlyticsStatusSource: CrashlyticsStatusSource,
) : ViewModel() {
    private val _settingsScreen =
        MutableLiveData<ModelContainer<SettingsScreenModel>>(LoadingModelContainer())
    val settingsScreen: LiveData<ModelContainer<SettingsScreenModel>>
        get() = _settingsScreen

    init {
        updateScreen()
    }

    private fun updateScreen() {
        _settingsScreen.value = SettingsScreenModel(
            apiKey = apiKeySource.apiKey,
            isCrashlyticsEnabled = crashlyticsStatusSource.isCrashlyticsEnabled()
        )
    }

    fun updateCrashlytics(enabled: Boolean) {
        crashlyticsStatusSource.setIsCrashlyticsEnabled(enabled)
        updateScreen()
    }

    fun updateApiKey(key: String) {
        apiKeySource.apiKey = key
        updateScreen()
    }
}