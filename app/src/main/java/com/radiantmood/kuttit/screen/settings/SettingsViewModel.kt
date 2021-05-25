package com.radiantmood.kuttit.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.dev.getApiKeyOrEmpty

class SettingsViewModel : ViewModel() {

    private val _apiKeyLiveData: MutableLiveData<String?> = MutableLiveData(getApiKeyOrEmpty())
    val apiKeyLiveData: LiveData<String?> get() = _apiKeyLiveData

    fun updateApiKey(key: String) {
        _apiKeyLiveData.value = key
    }
}