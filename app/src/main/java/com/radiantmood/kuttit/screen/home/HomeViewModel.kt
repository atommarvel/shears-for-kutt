package com.radiantmood.kuttit.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.radiantmood.kuttit.data.local.LoadingModelContainer
import com.radiantmood.kuttit.data.local.ModelContainer
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.network.KuttService
import com.radiantmood.kuttit.repo.KuttApiKeySource
import com.radiantmood.kuttit.util.snackbar.postSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val apiKeySource: KuttApiKeySource,
    private val kuttService: KuttService,
    private val kuttLinkSourceProvider: Provider<KuttLinkSource>,
) : ViewModel() {

    private var _homeScreen =
        MutableLiveData<ModelContainer<HomeScreenModel>>(LoadingModelContainer())
    val homeScreen: LiveData<ModelContainer<HomeScreenModel>> get() = _homeScreen

    private var _modifiers = MutableLiveData<Map<String, KuttLinkModifier>>(emptyMap())
    val modifiers: LiveData<Map<String, KuttLinkModifier>> get() = _modifiers

    private var kuttLinkPager: Pager<Int, KuttLink> = createPager()

    init {
        getLinks()
    }

    private fun createPager() = Pager(PagingConfig(10)) { kuttLinkSourceProvider.get() }

    private fun getLinks() = viewModelScope.launch(Dispatchers.IO) {
        _homeScreen.postValue(LoadingModelContainer())
        _modifiers.postValue(emptyMap())
        kuttLinkPager = createPager()
        val apiKey = apiKeySource.apiKey
        if (apiKey.isBlank()) {
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
            addDeletionModifier(link)
            kuttService.deleteLink(link.id)
            postSnackbar("Link deleted.")
            getLinks() // TODO#1knqy95: play nicer with paging to not reload entire screen when getting fresh data
        } catch (e: Exception) {
            postSnackbar(e)
        }
    }

    private fun addDeletionModifier(link: KuttLink) {
        val mods: MutableMap<String, KuttLinkModifier> =
            _modifiers.value?.toMutableMap() ?: mutableMapOf()
        mods[link.id] = KuttLinkModifier(link.id, true)
        _modifiers.postValue(mods)
    }
}