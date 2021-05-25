package com.radiantmood.kuttit.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.ui.component.KuttTopAppBar

val LocalSettingsViewModel = compositionLocalOf<SettingsViewModel> { error("No SettingsViewModel") }

@Composable
fun SettingsScreenRoot() {
    val vm: SettingsViewModel = viewModel()
    CompositionLocalProvider(
        LocalSettingsViewModel provides vm
    ) {
        SettingsScreen()
    }
}

@Composable
fun SettingsScreen() {
    Column {
        KuttTopAppBar(title = "Settings")
        Column(Modifier.padding(16.dp)) {
            ApiKeyField()
        }
    }
}

@Composable
fun ApiKeyField() {
    val vm = LocalSettingsViewModel.current
    val apiKey by vm.apiKeyLiveData.observeAsState()
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Api Key") },
        value = apiKey.orEmpty(),
        onValueChange = { vm.updateApiKey(it) },
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    "show/hide key"
                )
            }
        },
    )
}