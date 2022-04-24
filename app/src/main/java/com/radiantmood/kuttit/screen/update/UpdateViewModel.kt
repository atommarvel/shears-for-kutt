package com.radiantmood.kuttit.screen.update

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.data.local.LoadingModelContainer
import com.radiantmood.kuttit.data.local.ModelContainer
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.data.server.PostLinkBody
import com.radiantmood.kuttit.network.KuttService
import com.radiantmood.kuttit.ui.component.snackbar.postSnackbar
import com.radiantmood.kuttit.ui.component.snackbar.postSnackbarBuffer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface UpdateActions {
    fun onTargetUrlChanged(newUrl: String)
    fun onPathChanged(newPath: String)
    fun onDescriptionChanged(newDescription: String)
    fun onExpiresChanged(newExpires: String)
}

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val app: Application,
    private val kuttService: KuttService,
) : ViewModel(), UpdateActions {
    private var _updateScreen =
        MutableLiveData<ModelContainer<UpdateScreenModel>>(LoadingModelContainer())
    val updateScreen: LiveData<ModelContainer<UpdateScreenModel>> get() = _updateScreen
    private val screenModel: UpdateScreenModel? get() = (_updateScreen.value as? UpdateScreenModel)
    private lateinit var kuttLink: KuttLink

    init {
        setupDefaultModel()
    }

    private fun setupDefaultModel() {
        _updateScreen.value = UpdateScreenModel(
            targetUrl = "",
            path = "",
            expires = "",
            description = "",
            fieldsEnabled = true,
        )
    }

    fun setLink(link: KuttLink) {
        kuttLink = link
        onChange {
            copy(
                path = link.getPath().orEmpty(),
                targetUrl = link.target,
                expires = link.expire_in.orEmpty(),
                description = link.description.orEmpty(),
            )
        }
    }

    fun updateLink(nav: NavHostController) {
        viewModelScope.launch {
            try {
                setFieldsEnabled(false)
                val model = checkNotNull(screenModel) { app.getString(R.string.snackbar_generic_error) }
                // TODO: better way to fail gracefully if delete succeeds but post doesn't?
                kuttService.deleteLink(kuttLink.id)
                kuttService.postLink(model.toPostLinkBody(kuttLink))
                nav.postSnackbarBuffer("${model.path} successfully updated.")
                nav.popBackStack()
            } catch (e: Exception) {
                postSnackbar(e)
            } finally {
                setFieldsEnabled(true)
            }
        }
    }

    private fun UpdateScreenModel.toPostLinkBody(originalLink: KuttLink) = PostLinkBody(
        targetUrl = targetUrl,
        description = description,
        expires = expires,
        path = path,
        domain = originalLink.domain,
        password = null,
    )

    private fun KuttLink.getPath(): String? = try {
        Uri.parse(link).path?.removePrefix("/")
    } catch (e: Exception) {
        null
    }

    override fun onTargetUrlChanged(newUrl: String) = onChange { copy(targetUrl = newUrl) }

    override fun onPathChanged(newPath: String) = onChange { copy(path = newPath) }

    override fun onExpiresChanged(newExpires: String) = onChange { copy(expires = newExpires) }

    override fun onDescriptionChanged(newDescription: String) = onChange { copy(description = newDescription) }

    private fun setFieldsEnabled(enabled: Boolean) = onChange { copy(fieldsEnabled = enabled) }

    private inline fun onChange(block: UpdateScreenModel.() -> UpdateScreenModel) {
        screenModel?.let {
            _updateScreen.postValue(it.block())
        }
    }
}