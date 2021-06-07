package com.radiantmood.kuttit.screen.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer

class CreateViewModel : ViewModel() {
    private var _createScreen =
        MutableLiveData<ModelContainer<CreateScreenModel>>(LoadingModelContainer())
    val createScreen: LiveData<ModelContainer<CreateScreenModel>> get() = _createScreen
    private val model: CreateScreenModel? get() = (_createScreen.value as? CreateScreenModel)

    init {
        setupDefaultModel()
    }

    private fun setupDefaultModel() {
        _createScreen.value = CreateScreenModel(
            targetUrl = null, // TODO: could we grab the data from the clipboard?
            currentDomain = KUTT_IT, // TODO: allow user to default to their custom domain in domain management.
            domains = listOf(KUTT_IT),
            path = null,
            password = null,
            expires = null,
            description = null
        )
    }

    fun onTargetUrlChanged(url: String?) = onChange { it.copy(targetUrl = url) }

    fun onDomainChanged(domain: String) = onChange { it.copy(currentDomain = domain) }

    fun onPathChanged(path: String?) = onChange { it.copy(path = path) }

    fun onPasswordChanged(password: String?) = onChange { it.copy(password = password) }

    fun onExpiresChanged(expires: String?) = onChange { it.copy(expires = expires) }

    fun onDescriptionChanged(description: String?) = onChange { it.copy(description = description) }

    private inline fun onChange(block: (CreateScreenModel) -> CreateScreenModel) {
        model?.let {
            _createScreen.value = block(it)
        }
    }

    companion object {
        // TODO centralize kutt.it usage in a repo to prep for self hosting
        private const val KUTT_IT = "kutt.it"
    }
}