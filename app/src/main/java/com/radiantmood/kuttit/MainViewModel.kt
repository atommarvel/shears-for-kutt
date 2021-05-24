package com.radiantmood.kuttit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiantmood.kuttit.data.KuttLinkResponse
import com.radiantmood.kuttit.data.RetrofitBuilder.kuttService
import com.radiantmood.kuttit.dev.getApiKeyOrEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _apiKeyLiveData: MutableLiveData<String?> = MutableLiveData(getApiKeyOrEmpty())
    val apiKeyLiveData: LiveData<String?> get() = _apiKeyLiveData

    private val _linksLiveData: MutableLiveData<KuttLinkResponse> = MutableLiveData()
    val linksLiveData: LiveData<KuttLinkResponse> get() = _linksLiveData

    fun updateApiKey(key: String) {
        _apiKeyLiveData.value = key
    }

    fun getLinks() = viewModelScope.launch(Dispatchers.IO) {
        val links = kuttService.getLinks(apiKeyLiveData.value.orEmpty())

        _linksLiveData.postValue(links)
    }
}