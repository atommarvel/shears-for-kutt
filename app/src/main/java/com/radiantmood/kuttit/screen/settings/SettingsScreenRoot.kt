package com.radiantmood.kuttit.screen.settings

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar

private val LocalSettingsViewModel =
    compositionLocalOf<SettingsViewModel> { error("No SettingsViewModel") }

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
    val (showHelpDialog, setShowHelpDialog) = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { SettingsAppBar(setShowHelpDialog) },
    ) {
        SettingsBody(showHelpDialog, setShowHelpDialog)
    }
}

@Composable
fun SettingsAppBar(setShowHelpDialog: (Boolean) -> Unit) {
    KuttTopAppBar(title = "Settings") {
        AppBarAction(imageVector = Icons.Default.Help, contentDescription = "Help") {
            setShowHelpDialog(true)
        }
    }
}

@Composable
fun SettingsBody(showHelpDialog: Boolean, setShowHelpDialog: (Boolean) -> Unit) {
    Column {
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
                Report()
                Divider()
            }
            item {
                OpenSourceAttribution()
                Divider()
            }
        }
        HelpDialog(show = showHelpDialog, updateShow = setShowHelpDialog)
    }
}

@Composable
fun HelpDialog(show: Boolean, updateShow: (Boolean) -> Unit) {
    if (show) {
        Dialog(onDismissRequest = { updateShow(false) }) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "About API Key",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        """
                    While logged in, your API key can be found at https://kutt.it/settings in the "API" section. It will look like a string of nonsense.
                    
                    Paste your api key into the "API Key" field in settings to allow the app to talk to Kutt servers on your behalf. It's a lot like logging into Kutt. 
                    
                    DO NOT share this key with sources/people you don't trust!
                """.trimIndent()
                    )
                }
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
        label = { Text("API Key") },
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
        }
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
            Text("Disclosure", style = MaterialTheme.typography.h5)
            Text(
                """
                    Shears is an unofficial app not managed by the creators of Kutt.it. If you are experiencing issues with Kutt.it itself, please reach out to the Kutt.it maintainers themselves.
                    
                    Conversely, if you are experiencing issues with Shears, please reach out to the Shears app dev instead of the Kutt team! 
                """.trimIndent()
            )
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
fun SettingsRowUrl(text: String, url: String) {
    val ctx = LocalContext.current
    SettingsRow(text) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(ctx, intent, null)
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
    SettingsRowUrl(text = "Go to https://kutt.it", url = "https://kutt.it")
}

@Composable
fun Contact() {
    SettingsRowUrl(
        text = "Contact Shears app dev on Twitter",
        url = "https://twitter.com/radiantmood"
    )
}

@Composable
fun Report() {
    SettingsRowUrl(
        text = "Report abuses, malware and phishing Kutt links",
        url = "https://kutt.it/report"
    )
}

@Composable
fun OpenSourceAttribution() {
    SettingsRow("Open Source Attributions") {
        // TODO: nav to attribution
    }
}
