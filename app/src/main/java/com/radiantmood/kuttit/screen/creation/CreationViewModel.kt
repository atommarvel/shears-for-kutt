package com.radiantmood.kuttit.screen.creation

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.data.NewKuttLinkBody
import com.radiantmood.kuttit.data.RetrofitBuilder.kuttService
import com.radiantmood.kuttit.repo.ApiKeyRepo
import com.radiantmood.kuttit.repo.KuttBaseUrlRepo
import com.radiantmood.kuttit.util.snackbar.postSnackbar
import com.radiantmood.kuttit.util.snackbar.postSnackbarBuffer
import kotlinx.coroutines.launch

class CreationViewModel : ViewModel() {
    private var _creationScreen =
        MutableLiveData<ModelContainer<CreationScreenModel>>(LoadingModelContainer())
    val creationScreen: LiveData<ModelContainer<CreationScreenModel>> get() = _creationScreen
    private val screenModel: CreationScreenModel? get() = (_creationScreen.value as? CreationScreenModel)

    init {
        setupDefaultModel()
    }

    private fun setupDefaultModel() {
        _creationScreen.value = CreationScreenModel(
            targetUrl = "", // TODO: could we grab the data from the clipboard?
            currentDomain = 0, // TODO: allow user to default to their custom domain in domain management.
            domains = listOfNotNull(
                KuttBaseUrlRepo.baseUrl,
                "radiantmood.com", // TODO: get domains from the domain repository
            ),
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

    private inline fun onChange(block: (CreationScreenModel) -> CreationScreenModel) {
        screenModel?.let {
            _creationScreen.postValue(block(it))
        }
    }

    private fun setFieldsEnabled(enabled: Boolean) = onChange { it.copy(fieldsEnabled = enabled) }

    private fun createNewKuttLinkBody(model: CreationScreenModel): NewKuttLinkBody = with(model) {
        NewKuttLinkBody(
            targetUrl = targetUrl,
            description = description,
            expires = expires,
            password = password,
            path = path,
            domain = if (currentDomain != 0) domains[currentDomain] else null,
        )
    }

    fun createLink(
        nav: NavHostController,
        clipboardManager: ClipboardManager? = null,
    ) = viewModelScope.launch {
        setFieldsEnabled(false)
        try {
            val apiKey = checkNotNull(ApiKeyRepo.apiKey) { "API key is missing" }
            val model = checkNotNull(screenModel) { "Something went wrong" }
            val creation = kuttService.postLink(apiKey, createNewKuttLinkBody(model))
            clipboardManager?.setText(AnnotatedString(creation.link))
            nav.postSnackbarBuffer("Shortened link copied to clipboard.")
            nav.popBackStack()
        } catch (e: Exception) {
            postSnackbar(e)
        } finally {
            setFieldsEnabled(true)
        }
    }
}