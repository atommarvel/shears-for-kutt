package com.radiantmood.kuttit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiantmood.kuttit.data.KuttResponse
import com.radiantmood.kuttit.data.RetrofitBuilder.kuttService
import com.radiantmood.kuttit.dev.getApiKeyOrEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _apiKeyLiveData: MutableLiveData<String?> = MutableLiveData(getApiKeyOrEmpty())
    val apiKeyLiveData: LiveData<String?> get() = _apiKeyLiveData

    private val _linksLiveData: MutableLiveData<KuttResponse> = MutableLiveData()
    val linksLiveData: LiveData<KuttResponse> get() = _linksLiveData

    fun updateApiKey(key: String) {
        _apiKeyLiveData.value = key
    }

    fun getLinks() = viewModelScope.launch(Dispatchers.IO) {
        val links = kuttService.getLinks(apiKeyLiveData.value.orEmpty())
        Log.d("araiff", "getLinks: $links")
        _linksLiveData.postValue(links)
    }
}