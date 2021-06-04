package com.radiantmood.kuttit.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiantmood.kuttit.data.KuttLink
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.data.RetrofitBuilder.kuttService
import com.radiantmood.kuttit.repo.ApiKeyRepo
import com.radiantmood.kuttit.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _homeScreen =
        MutableLiveData<ModelContainer<HomeScreenModel>>(LoadingModelContainer())
    val homeScreen: LiveData<ModelContainer<HomeScreenModel>> get() = _homeScreen

    private var _snackbar = MutableLiveData<Event<String>>()
    val snackbar: LiveData<Event<String>> get() = _snackbar

    init {
        getLinks()
    }

    fun getLinks() = viewModelScope.launch(Dispatchers.IO) {
        _homeScreen.postValue(LoadingModelContainer())
        val apiKey = ApiKeyRepo.apiKey
        if (apiKey.isNullOrBlank()) {
            _homeScreen.postValue(HomeScreenModel.ApiKeyMissing)
        } else {
            // TODO: try/catch system for requests to prevent crashes
            val response = kuttService.getLinks(ApiKeyRepo.apiKey.orEmpty())
            // TODO: if you have no links, is data missing or an empty array?
            val items = mutableListOf<KuttLink>().apply {
                addAll(response.data)
            }
            _homeScreen.postValue(HomeScreenModel.Content(items))
        }
    }

    fun closeDialog() {
        val container = _homeScreen.value
        if (container is HomeScreenModel.Content && container.dialogLink != null) {
            _homeScreen.value = container.copy(dialogLink = null)
        }
    }

    fun openDialog(link: KuttLink) {
        val container = _homeScreen.value
        if (container is HomeScreenModel.Content && container.dialogLink == null) {
            _homeScreen.value = container.copy(dialogLink = link)
        }
    }
}