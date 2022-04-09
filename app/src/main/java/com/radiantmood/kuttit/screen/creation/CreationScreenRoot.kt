package com.radiantmood.kuttit.screen.creation

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalRootViewModel
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.RootCommon
import com.radiantmood.kuttit.data.local.LoadingModelContainer
import com.radiantmood.kuttit.data.local.ModelContainer
import com.radiantmood.kuttit.nav.navTo
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.ui.component.snackbar.KuttSnackbar
import com.radiantmood.kuttit.util.ModelContainerContent

@Composable
fun CreationScreenRoot(target: String) {
    val vm: CreationViewModel = hiltViewModel()
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
    val rvm = LocalRootViewModel.current
    return remember {
        CreationActionsImpl(vm, object : CreationNav {
            override fun navToDomainManagement() {
                // TODO#zbbn26: nav to domain management screen instead
                nav.navTo(rvm.settingsDestinationNavRoute())
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