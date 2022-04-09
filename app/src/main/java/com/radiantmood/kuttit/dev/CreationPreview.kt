package com.radiantmood.kuttit.dev

import androidx.compose.ui.platform.ClipboardManager
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.screen.creation.CreationActions
import com.radiantmood.kuttit.screen.creation.CreationScreenModel

val creationScreenModelPreview = CreationScreenModel(
    targetUrl = "atommarvel.com",
    currentDomain = 0,
    domains = listOf("first.com", "second.com"),
    path = "blog",
    password = "super secret",
    expires = "1 day",
    description = "preview of creation description",
    fieldsEnabled = true
)

class CreationActionsPreview : CreationActions {
    override fun onTargetUrlChanged(url: String) {}
    override fun onDomainChanged(domain: Int) {}
    override fun onPathChanged(path: String) {}
    override fun onPasswordChanged(password: String) {}
    override fun onExpiresChanged(expires: String) {}
    override fun onDescriptionChanged(description: String) {}
    override fun createLink(nav: NavHostController, clipboardManager: ClipboardManager?) {}
    override fun navToDomainManagement() {}
}