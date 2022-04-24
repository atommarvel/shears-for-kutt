package com.radiantmood.kuttit.screen.update

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.RootCommon
import com.radiantmood.kuttit.data.local.LoadingModelContainer
import com.radiantmood.kuttit.data.local.ModelContainer
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.ui.component.LazySpacer
import com.radiantmood.kuttit.ui.component.snackbar.KuttSnackbar
import com.radiantmood.kuttit.util.ModelContainerContent

private val LocalUpdateViewModel = compositionLocalOf<UpdateViewModel> { error("No UpdateViewModel") }

@Composable
fun UpdateScreenRoot(initialKuttLink: KuttLink) {
    val vm: UpdateViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(initialKuttLink) {
        vm.setLink(initialKuttLink)
    }

    RootCommon()
    CompositionLocalProvider(
        LocalUpdateViewModel provides vm,
        LocalScaffoldState provides scaffoldState,
    ) {
        UpdateScreen()
    }
}

@Composable
fun UpdateScreen() {
    val vm = LocalUpdateViewModel.current
    val modelContainer by vm.updateScreen.observeAsState(LoadingModelContainer())
    Scaffold(
        topBar = { UpdateAppBar() },
        scaffoldState = LocalScaffoldState.current
    ) {
        KuttSnackbar()
        UpdateBody(modelContainer, vm)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UpdateAppBar() {
    val keyboardCtrl = LocalSoftwareKeyboardController.current
    val vm = LocalUpdateViewModel.current
    val nav = LocalNavController.current
    KuttTopAppBar(title = "Update Link") {
        AppBarAction(
            imageVector = Icons.Default.Send,
            contentDescription = "Update Link"
        ) {
            vm.updateLink(nav)
            keyboardCtrl?.hide()
        }
    }
}

@Composable
fun UpdateBody(modelContainer: ModelContainer<UpdateScreenModel>, actions: UpdateActions) {
    ModelContainerContent(modelContainer) { screenModel ->
        UpdateForm(
            model = screenModel,
            actions = actions
        )
    }
}

@Composable
fun UpdateForm(model: UpdateScreenModel, actions: UpdateActions) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(
                value = model.targetUrl,
                onValueChange = actions::onTargetUrlChanged,
                label = { Text(stringResource(id = R.string.url_label)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = model.fieldsEnabled,
            )
        }
        LazySpacer(Modifier.height(16.dp))
        item {
            TextField(
                value = model.path,
                onValueChange = actions::onPathChanged,
                label = { Text(stringResource(R.string.custom_path)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = model.fieldsEnabled,
            )
        }
        LazySpacer(Modifier.height(16.dp))
        item {
            TextField(
                value = model.description,
                onValueChange = actions::onDescriptionChanged,
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = model.fieldsEnabled,
            )
        }
        LazySpacer(Modifier.height(16.dp))
        item {
            TextField(
                value = model.expires,
                onValueChange = actions::onExpiresChanged,
                label = { Text(stringResource(R.string.expire_in)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.expire_in_placeholder)) },
                enabled = model.fieldsEnabled,
            )
        }
    }
}