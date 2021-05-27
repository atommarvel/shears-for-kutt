package com.radiantmood.kuttit.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.repo.ApiKeyRepo

class SettingsViewModel : ViewModel() {

    private val _apiKeyLiveData: MutableLiveData<String?> = MutableLiveData(ApiKeyRepo.apiKey)
    val apiKeyLiveData: LiveData<String?> get() = _apiKeyLiveData

    fun updateApiKey(key: String) {
        ApiKeyRepo.apiKey = key
        _apiKeyLiveData.value = key
    }
}