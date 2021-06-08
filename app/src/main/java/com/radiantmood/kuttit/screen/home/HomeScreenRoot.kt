package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.radiantmood.kuttit.CreateScreen
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.SettingsScreen
import com.radiantmood.kuttit.data.KuttLink
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.navigate
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.util.Fullscreen
import com.radiantmood.kuttit.util.KuttSnackbar
import com.radiantmood.kuttit.util.LoadingScreen
import com.radiantmood.kuttit.util.ModelContainerContent
import com.radiantmood.kuttit.util.postSnackbar

private val LocalHomeViewModel =
    compositionLocalOf<HomeViewModel> { error("No HomeViewModel") }

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
        floatingActionButton = { Fab() },
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
fun Fab() {
    val nav = LocalNavController.current
    FloatingActionButton(
        onClick = { nav.navigate(CreateScreen) },
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Create new link")
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
    KuttSnackbar()
    LinkDialog(content.dialogLink)
}

@Composable
private fun UserLinkList(
    content: HomeScreenModel.Content
) {
    val scope = rememberCoroutineScope()
    val lazyLinks = content.kuttLinkPager.flow.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyLinks) { link ->
            KuttLinkCardListItem(link)
        }

        lazyLinks.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingScreen() }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp)) {
                            CircularProgressIndicator()
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = (loadState.refresh as LoadState.Error).error
                    item {
                        scope.postSnackbar(e)
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = (loadState.append as LoadState.Error).error
                    item {
                        scope.postSnackbar(e)
                    }
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

@Composable
fun KuttLinkCardListItem(link: KuttLink?) {
    val vm = LocalHomeViewModel.current
    link?.let {
        Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            KuttLinkCard(link) {
                vm.openDialog(link)
            }
        }
    }
}