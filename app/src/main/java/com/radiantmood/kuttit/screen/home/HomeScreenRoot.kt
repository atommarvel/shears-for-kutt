package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.SettingsScreen
import com.radiantmood.kuttit.data.KuttLink
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.navigate
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.util.Fullscreen
import com.radiantmood.kuttit.util.ModelContainerContent
import kotlinx.coroutines.launch

private val LocalHomeViewModel =
    compositionLocalOf<HomeViewModel> { error("No HomeViewModel") }
private val LocalScaffoldState =
    compositionLocalOf<ScaffoldState> { error("No ScaffoldState") }

@Composable
fun HomeScreenRoot() {
    val vm: HomeViewModel = viewModel()
    val scaffoldState = rememberScaffoldState()
    CompositionLocalProvider(
        LocalHomeViewModel provides vm,
        LocalScaffoldState provides scaffoldState
    ) {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    Scaffold(
        scaffoldState = LocalScaffoldState.current,
        topBar = { HomeAppBar() },
    ) {
        HomeBody()
    }
}

@Composable
fun HomeAppBar() {
    val nav = LocalNavController.current
    KuttTopAppBar(title = "Shears") {
        AppBarAction(imageVector = Icons.Default.Settings) {
            nav.navigate(SettingsScreen)
        }
    }
}

@Composable
fun HomeBody() {
    val vm = LocalHomeViewModel.current
    val modelContainer by vm.homeScreen.observeAsState(LoadingModelContainer())
    ModelContainerContent(modelContainer) { screenModel ->
        when (screenModel) {
            is HomeScreenModel.ApiKeyMissing -> ApiKeyMissing()
            is HomeScreenModel.Content -> UserLinks(screenModel)
        }
    }
}

@Composable
fun ApiKeyMissing() {
    val nav = LocalNavController.current
    Fullscreen {
        Text(
            """
                Shears currently only supports logged-in users. 
                Please go to settings and add your API key.
            """.trimIndent(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { nav.navigate(SettingsScreen) }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            Text("Settings")
        }
    }
}

@Composable
fun UserLinks(content: HomeScreenModel.Content) {
    Column(
        Modifier.fillMaxSize()
    ) {
        Overlays(content)
        Spacer(modifier = Modifier.height(16.dp))
        UserLinkList(content)
    }
}

@Composable
fun Overlays(content: HomeScreenModel.Content) {
    val vm = LocalHomeViewModel.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalScaffoldState.current.snackbarHostState
    val snackbarMessage by vm.snackbar.observeAsState()
    snackbarMessage?.getContentIfNotHandled()?.let {
        scope.launch {
            snackbarHostState.showSnackbar(it)
        }
    }
    LinkDialog(content.dialogLink)
}

@Composable
private fun UserLinkList(
    content: HomeScreenModel.Content
) {
    val vm = LocalHomeViewModel.current
    LazyColumn {
        items(content.links, key = { it.id }) { link ->
            // TODO: verify that beta08 fixes lazycolumn bugs :(
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                KuttLinkCard(link) {
                    vm.openDialog(link)
                }
            }
        }
    }
}

@Composable
fun LinkDialog(link: KuttLink?) {
    val vm = LocalHomeViewModel.current
    link?.let {
        Dialog(onDismissRequest = { vm.closeDialog() }) {
            Surface {
                Text("Hello ${link.target}")
            }
        }
    }
}