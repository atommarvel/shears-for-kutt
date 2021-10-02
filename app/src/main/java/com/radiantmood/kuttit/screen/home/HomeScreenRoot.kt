package com.radiantmood.kuttit.screen.home

import ComposableScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.RootCommon
import com.radiantmood.kuttit.data.KuttLink
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.nav.navigate
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.util.Fullscreen
import com.radiantmood.kuttit.util.ModelContainerContent
import com.radiantmood.kuttit.util.snackbar.KuttSnackbar
import com.radiantmood.kuttit.util.snackbar.postSnackbar

private val LocalHomeViewModel =
    compositionLocalOf<HomeViewModel> { error("No HomeViewModel") }

@Composable
fun HomeScreenRoot() {
    val vm: HomeViewModel = viewModel()
    val scaffoldState = rememberScaffoldState()
    RootCommon()
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
        floatingActionButton = { Fab() },
    ) {
        HomeBody()
    }
}

@Composable
fun HomeAppBar() {
    val nav = LocalNavController.current
    KuttTopAppBar(title = stringResource(R.string.app_name)) {
        AppBarAction(imageVector = Icons.Default.Settings,
            contentDescription = stringResource(R.string.settings)) {
            nav.navigate(ComposableScreen.SettingsScreen.route())
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
fun Fab() {
    val nav = LocalNavController.current
    FloatingActionButton(
        onClick = { nav.navigate(ComposableScreen.CreationScreen.route()) },
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.a11y_create_link)
        )
    }
}

@Composable
fun ApiKeyMissing() {
    val nav = LocalNavController.current
    Fullscreen {
        Text(
            text = stringResource(R.string.missing_api_msg),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { nav.navigate(ComposableScreen.SettingsScreen.route()) }) {
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
        UserLinkList(content)
    }
}

@Composable
fun Overlays(content: HomeScreenModel.Content) {
    val vm = LocalHomeViewModel.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val snackbarComingSoonMsg = stringResource(R.string.snackbar_update_coming_soon)
    val snackbarLinkCopiedMsg = stringResource(R.string.snackbar_link_copied_to_clipboard)
    KuttSnackbar()
    LinkDialog(
        link = content.dialogLink,
        copyToClipboard = {
            clipboardManager.setText(AnnotatedString(it))
            scope.postSnackbar(snackbarLinkCopiedMsg)
        },
        updateLink = {
            // TODO: link updating
            scope.postSnackbar(snackbarComingSoonMsg)
        },
        deleteLink = {
            vm.deleteLink(it)
        },
        closeDialog = {
            vm.closeDialog()
        }
    )
}

@Composable
private fun UserLinkList(
    content: HomeScreenModel.Content,
) {
    val vm = LocalHomeViewModel.current
    val lazyLinks = content.kuttLinkPager.flow.collectAsLazyPagingItems()
    val mods by vm.modifiers.observeAsState(emptyMap())
    LazyColumn {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        items(lazyLinks) { link -> KuttLinkCardListItem(link, mods) }
        lazyPagingFooter(lazyLinks, this)
    }
}

@Composable
fun KuttLinkCardListItem(link: KuttLink?, mods: Map<String, KuttLinkModifier>) {
    val vm = LocalHomeViewModel.current
    link?.let {
        Box(modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()) {
            KuttLinkCard(link, mods.containsKey(link.id)) {
                vm.openDialog(link)
            }
        }
    }
}