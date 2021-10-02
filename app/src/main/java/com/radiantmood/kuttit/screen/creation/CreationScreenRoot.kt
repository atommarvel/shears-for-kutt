package com.radiantmood.kuttit.screen.creation

import ComposableScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.RootCommon
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.nav.navigate
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.util.ModelContainerContent
import com.radiantmood.kuttit.util.snackbar.KuttSnackbar

@Composable
fun CreationScreenRoot(target: String) {
    val vm: CreationViewModel = viewModel()
    val modelContainer by vm.creationScreen.observeAsState(LoadingModelContainer())
    val actions = rememberCreationActions(vm)
    val scaffoldState = rememberScaffoldState()
    val startingTarget = remember { target }

    actions.onTargetUrlChanged(startingTarget)
    RootCommon()

    CompositionLocalProvider(
        LocalScaffoldState provides scaffoldState
    ) {
        CreationScreen(scaffoldState, actions) {
            CreationBody(modelContainer, actions)
        }
    }
}

@Composable
fun rememberCreationActions(vm: CreationViewModel): CreationActions {
    val nav = LocalNavController.current
    return remember {
        CreationActionsImpl(vm, object : CreationNav {
            override fun navToDomainManagement() {
                // TODO: nav to domain management screen instead
                nav.navigate(ComposableScreen.SettingsScreen.route())
            }
        })
    }
}

@Composable
fun CreationScreen(
    scaffoldState: ScaffoldState,
    actions: CreationActions,
    content: @Composable () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CreationAppBar(actions) }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            KuttSnackbar()
            content()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreationAppBar(actions: CreationActions) {
    val nav = LocalNavController.current
    val clipboardManager = LocalClipboardManager.current
    val keyboardCtrl = LocalSoftwareKeyboardController.current
    KuttTopAppBar(title = stringResource(R.string.create)) {
        AppBarAction(
            imageVector = Icons.Default.Send,
            contentDescription = stringResource(R.string.create_link)
        ) {
            actions.createLink(nav, clipboardManager)
            keyboardCtrl?.hide()
        }
    }
}

@Composable
fun CreationBody(modelContainer: ModelContainer<CreationScreenModel>, actions: CreationActions) {
    ModelContainerContent(modelContainer) { screenModel ->
        CreationForm(
            model = screenModel,
            actions = actions
        )
    }
}