package com.radiantmood.kuttit.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.radiantmood.kuttit.data.KuttLink
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.data.RetrofitBuilder.kuttService
import com.radiantmood.kuttit.repo.ApiKeyRepo
import com.radiantmood.kuttit.util.postSnackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _homeScreen =
        MutableLiveData<ModelContainer<HomeScreenModel>>(LoadingModelContainer())
    val homeScreen: LiveData<ModelContainer<HomeScreenModel>> get() = _homeScreen

    private var kuttLinkPager: Pager<Int, KuttLink> = createPager()

    init {
        getLinks()
    }

    private fun createPager() = Pager(PagingConfig(10)) { KuttLinkSource() }

    private fun getLinks() = viewModelScope.launch(Dispatchers.IO) {
        _homeScreen.postValue(LoadingModelContainer())
        kuttLinkPager = createPager()
        val apiKey = ApiKeyRepo.apiKey
        if (apiKey.isNullOrBlank()) {
            _homeScreen.postValue(HomeScreenModel.ApiKeyMissing)
        } else {
            _homeScreen.postValue(HomeScreenModel.Content(kuttLinkPager))
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

    fun deleteLink(link: KuttLink) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val apiKey = checkNotNull(ApiKeyRepo.apiKey) { "API key is missing." }
            // TODO: UX while user waits for deletion request to complete
            kuttService.deleteLink(apiKey, link.id)
            postSnackbar("Link deleted.")
            getLinks()
        } catch (e: Exception) {
            postSnackbar(e)
        }
    }
}