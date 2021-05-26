package com.radiantmood.kuttit.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
        // TODO: app bar help action explaining api key
        KuttTopAppBar(title = "Settings")
        LazyColumn(Modifier.padding(16.dp)) {
            item {
                ApiKeyField()
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Disclosure()
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Divider()
                ManageDomains()
                Divider()
            }
            item {
                KuttItLink()
                Divider()
            }
            item {
                Contact()
                Divider()
            }
            item {
                OpenSourceAttribution()
                Divider()
            }
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

@Composable
fun Disclosure() {
    Card(
        backgroundColor = MaterialTheme.colors.error
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // TODO: make disclosure stand out a tad more?
            Text("Disclosure", style = MaterialTheme.typography.h5)
            Text("This app is not managed by the creators of Kutt.it. If you are experiencing issues with Kutt.it itself, please reach out to the Kutt.it maintainers themselves.")
        }
    }
}

@Composable
fun SettingsRow(text: String, onClick: () -> Unit) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun ManageDomains() {
    SettingsRow("Manage domains") {
        // TODO: nav to manage domains screen
    }
}

@Composable
fun KuttItLink() {
    SettingsRow("Go to Kutt.it") {
        // TODO: nav to kutt.it
    }
}

@Composable
fun Contact() {
    SettingsRow("Contact Kutt Android Dev") {
        // TODO: nav to twitter
    }
}

@Composable
fun OpenSourceAttribution() {
    SettingsRow("Open Source Attributions") {
        // TODO: nav to attribution
    }
}
