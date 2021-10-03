package com.radiantmood.kuttit.screen.settings

import android.content.Intent
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.RootCommon
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.ui.component.ApiKeyInput
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.ui.component.LazyDivider
import com.radiantmood.kuttit.ui.component.LazySpacer
import com.radiantmood.kuttit.ui.component.PlatformDialog
import com.radiantmood.kuttit.util.ModelContainerContent
import com.radiantmood.kuttit.util.snackbar.postSnackbar

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
    KuttTopAppBar(title = stringResource(R.string.settings)) {
        AppBarAction(imageVector = Icons.Default.Help,
            contentDescription = stringResource(R.string.a11y_help)) {
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
                item { Translations() }
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

@Composable
fun HelpDialog(show: Boolean, updateShow: (Boolean) -> Unit) {
    if (show) {
        PlatformDialog(onDismissRequest = { updateShow(false) }) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.api_key_title),
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
    Card(backgroundColor = MaterialTheme.colors.error) {
        Column(Modifier.padding(8.dp)) {
            Text(stringResource(R.string.disclosure_title), style = MaterialTheme.typography.h5)
            Text(stringResource(R.string.ownership_disclosure))
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
    val scope = rememberCoroutineScope()
    val snackbarManageDomainsTodo = stringResource(R.string.manage_domains_todo)
    SettingsRow(onClick = { scope.postSnackbar(snackbarManageDomainsTodo) }) {
        Text(stringResource(R.string.manage_domains))
    }
}

@Composable
fun KuttItLink() {
    SettingsRowUrl(
        text = stringResource(R.string.go_to_kutt),
        url = "https://kutt.it"
    )
}

@Composable
fun Contact() {
    SettingsRowUrl(
        text = stringResource(R.string.twitter_contact),
        url = "https://twitter.com/radiantmood"
    )
}

@Composable
fun Translations() {
    SettingsRowUrl(
        text = stringResource(R.string.translation_contact),
        url = "https://poeditor.com/join/project?hash=Tw8KdfZZno",
    )
}

@Composable
fun Report() {
    SettingsRowUrl(
        text = stringResource(R.string.report),
        url = "https://kutt.it/report"
    )
}

// TODO#1ney68w: move to using CrashlyticsOptInRow component
@Composable
fun Crashlytics(enabled: Boolean) {
    val vm = LocalSettingsViewModel.current
    val status = if (enabled) R.string.crashlytics_enabled else R.string.crashlytics_disabled
    SettingsRow(onClick = {
        vm.updateCrashlytics(!enabled)
    }) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(status)
        )
        Switch(
            checked = enabled,
            onCheckedChange = { vm.updateCrashlytics(!enabled) }
        )
    }
}

@Composable
fun OpenSourceAttribution() {
    val context = LocalContext.current
    SettingsRow(onClick = {
        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
    }) {
        Text(stringResource(R.string.open_source))
    }
}
