package com.radiantmood.kuttit.screen.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.util.postSnackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            targetUrl = "", // TODO: could we grab the data from the clipboard?
            currentDomain = 0, // TODO: allow user to default to their custom domain in domain management.
            domains = listOf(KUTT_IT),
            path = "",
            password = "",
            expires = "",
            description = "",
            fieldsEnabled = true,
        )
    }

    fun onTargetUrlChanged(url: String) = onChange { it.copy(targetUrl = url) }

    fun onDomainChanged(domain: Int) = onChange { it.copy(currentDomain = domain) }

    fun onPathChanged(path: String) = onChange { it.copy(path = path) }

    fun onPasswordChanged(password: String) = onChange { it.copy(password = password) }

    fun onExpiresChanged(expires: String) = onChange { it.copy(expires = expires) }

    fun onDescriptionChanged(description: String) = onChange { it.copy(description = description) }

    private inline fun onChange(block: (CreateScreenModel) -> CreateScreenModel) {
        model?.let {
            _createScreen.postValue(block(it))
        }
    }

    private fun setFieldAbleness(enabled: Boolean) = onChange { it.copy(fieldsEnabled = enabled) }

    fun createLink(nav: NavHostController) = viewModelScope.launch {
        // RESUME
        // TODO: update UI to show we are talking to Kutt. Disable all fields?
        setFieldAbleness(false)
        // TODO: make api call
        postSnackbar("TODO: This is the part where a network call gets made")
        delay(1000L)
        // TODO: navigate back home with UX indicating success
        // TODO: if failed, show snackbar and re-enable UI
        setFieldAbleness(true)
    }

    companion object {
        // TODO centralize kutt.it usage in a repo to prep for self hosting
        private const val KUTT_IT = "kutt.it"
    }
}