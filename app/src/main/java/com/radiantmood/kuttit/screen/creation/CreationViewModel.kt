package com.radiantmood.kuttit.screen.creation

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.data.NewKuttLinkBody
import com.radiantmood.kuttit.data.RetrofitBuilder.kuttService
import com.radiantmood.kuttit.kuttApp
import com.radiantmood.kuttit.repo.SettingsRepo
import com.radiantmood.kuttit.util.snackbar.postSnackbar
import com.radiantmood.kuttit.util.snackbar.postSnackbarBuffer
import kotlinx.coroutines.launch

interface CreationInputs {
    fun onTargetUrlChanged(url: String)
    fun onDomainChanged(domain: Int)
    fun onPathChanged(path: String)
    fun onPasswordChanged(password: String)
    fun onExpiresChanged(expires: String)
    fun onDescriptionChanged(description: String)
    fun createLink(nav: NavHostController, clipboardManager: ClipboardManager?)
}

interface CreationNav {
    fun navToDomainManagement()
}

interface CreationActions : CreationInputs, CreationNav

class CreationActionsImpl(
    private val inputs: CreationInputs,
    private val nav: CreationNav,
) : CreationActions, CreationInputs by inputs, CreationNav by nav

class CreationViewModel : ViewModel(), CreationInputs {
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
                SettingsRepo.baseUrl,
                "radiantmood.com", // TODO: get domains from the domain repository
            ),
            path = "",
            password = "",
            expires = "",
            description = "",
            fieldsEnabled = true,
        )
    }

    override fun onTargetUrlChanged(url: String) = onChange { it.copy(targetUrl = url) }

    override fun onDomainChanged(domain: Int) = onChange { it.copy(currentDomain = domain) }

    override fun onPathChanged(path: String) = onChange { it.copy(path = path) }

    override fun onPasswordChanged(password: String) = onChange { it.copy(password = password) }

    override fun onExpiresChanged(expires: String) = onChange { it.copy(expires = expires) }

    override fun onDescriptionChanged(description: String) =
        onChange { it.copy(description = description) }

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

    override fun createLink(nav: NavHostController, clipboardManager: ClipboardManager?) {
        viewModelScope.launch {
            setFieldsEnabled(false)
            try {
                val model =
                    checkNotNull(screenModel) { kuttApp.getString(R.string.snackbar_generic_error) }
                val creation = kuttService.postLink(createNewKuttLinkBody(model))
                clipboardManager?.setText(AnnotatedString(creation.link))
                nav.postSnackbarBuffer(kuttApp.getString(R.string.snackbar_link_copied))
                nav.popBackStack()
            } catch (e: Exception) {
                postSnackbar(e)
            } finally {
                setFieldsEnabled(true)
            }
        }
    }
}