package com.radiantmood.kuttit.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.RootCommon
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.ui.component.ApiKeyInput
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.ui.component.PlatformDialog
import com.radiantmood.kuttit.util.ModelContainerContent

private val LocalSettingsViewModel =
    compositionLocalOf<SettingsViewModel> { error("No SettingsViewModel") }

@Composable
fun SettingsScreenRoot() {
    val vm: SettingsViewModel = viewModel()
    RootCommon()
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
    val vm = LocalSettingsViewModel.current
    val modelContainer by vm.settingsScreen.observeAsState(LoadingModelContainer())
    ModelContainerContent(modelContainer) { screenModel ->
        Column {
            LazyColumn(Modifier.padding(16.dp)) {
                ApiKeyField(screenModel.apiKey)
                LazySpacer(modifier = Modifier.height(16.dp))
                item { Disclosure() }
                LazySpacer(modifier = Modifier.height(16.dp))
                LazyDivider()
                item { ManageDomains() }
                LazyDivider()
                item { KuttItLink() }
                LazyDivider()
                item { Contact() }
                LazyDivider()
                item { Report() }
                LazyDivider()
                item { Crashlytics(screenModel.isCrashlyticsEnabled) }
                LazyDivider()
                item { OpenSourceAttribution() }
                LazyDivider()
            }
            HelpDialog(show = showHelpDialog, updateShow = setShowHelpDialog)
        }
    }
}

fun LazyListScope.LazyDivider(modifier: Modifier = Modifier) = item {
    Divider(modifier = modifier)
}

fun LazyListScope.LazySpacer(modifier: Modifier = Modifier) = item {
    Spacer(modifier = modifier)
}

@Composable
fun HelpDialog(show: Boolean, updateShow: (Boolean) -> Unit) {
    if (show) {
        PlatformDialog(onDismissRequest = { updateShow(false) }) {
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
                    Text(stringResource(R.string.api_explanation))
                }
            }
        }
    }
}

fun LazyListScope.ApiKeyField(apiKey: String?) = item {
    val vm = LocalSettingsViewModel.current
    ApiKeyInput(apiKey = apiKey) { vm.updateApiKey(it) }
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
fun SettingsRow(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        content()
    }
}

@Composable
fun SettingsRowUrl(text: String, url: String) {
    val uriHandler = LocalUriHandler.current
    SettingsRow(onClick = { uriHandler.openUri(url) }) {
        Text(text)
    }
}

@Composable
fun ManageDomains() {
    SettingsRow(onClick = { /*TODO*/ }) {
        Text("Manage domains")
    }
}

@Composable
fun KuttItLink() {
    SettingsRowUrl(
        text = "Go to https://kutt.it",
        url = "https://kutt.it"
    )
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

// TODO: move to using CrashlyticsOptInRow component
@Composable
fun Crashlytics(enabled: Boolean) {
    val vm = LocalSettingsViewModel.current
    val status = if (enabled) "enabled" else "disabled"
    SettingsRow(onClick = {
        vm.updateCrashlytics(!enabled)
    }) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Crashlytics $status"
        )
        Switch(
            checked = enabled,
            onCheckedChange = { vm.updateCrashlytics(!enabled) }
        )
    }
}

@Composable
fun OpenSourceAttribution() {
    SettingsRow(onClick = { /*TODO: nav to attribution*/ }) {
        Text("Open Source Attributions")
    }
}
