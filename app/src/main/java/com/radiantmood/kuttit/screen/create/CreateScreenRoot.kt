package com.radiantmood.kuttit.screen.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.SettingsScreen
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.navigate
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.util.ModelContainerContent

private val LocalCreateViewModel =
    compositionLocalOf<CreateViewModel> { error("No CreateViewModel") }

@Composable
fun CreateScreenRoot() {
    val vm: CreateViewModel = viewModel()
    val scaffoldState = rememberScaffoldState()
    CompositionLocalProvider(
        LocalCreateViewModel provides vm,
        LocalScaffoldState provides scaffoldState
    ) {
        CreateScreen()
    }
}

@Composable
fun CreateScreen() {
    // RESUME
    // TODO: set up scaffold
    // TODO: set up snackbar

    Scaffold(
        scaffoldState = LocalScaffoldState.current,
        topBar = { CreateAppBar() }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            CreateBody()
        }
    }
}

@Composable
fun CreateAppBar() {
    val nav = LocalNavController.current
    val vm = LocalCreateViewModel.current
    KuttTopAppBar(title = "Create") {
        AppBarAction(imageVector = Icons.Default.Send) {
            vm.createLink(nav)
        }
    }
}

@Composable
fun CreateBody() {
    val vm = LocalCreateViewModel.current
    val modelContainer by vm.createScreen.observeAsState(LoadingModelContainer())
    ModelContainerContent(modelContainer) { screenModel ->
        CreateForm(screenModel)
    }
}

@Composable
fun CreateForm(model: CreateScreenModel) {
    val vm = LocalCreateViewModel.current
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TextField(
                value = model.targetUrl,
                onValueChange = { vm.onTargetUrlChanged(it) },
                label = { Text("URL to shorten") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32.dp))
        }
        item {
            Text("Optional Advanced Options:", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }
        item {
            DomainDropdown(model)
            Spacer(Modifier.height(16.dp))
        }
        item {
            TextField(
                value = model.path,
                onValueChange = { vm.onPathChanged(it) },
                label = { Text("Custom path") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }
        item {
            TextField(
                value = model.password,
                onValueChange = { vm.onPasswordChanged(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }
        item {
            TextField(
                value = model.expires,
                onValueChange = { vm.onExpiresChanged(it) },
                label = { Text("Expire in") },
                placeholder = { Text("2 minutes/hours/days") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }
        item {
            TextField(
                value = model.description,
                onValueChange = { vm.onDescriptionChanged(it) },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun DomainDropdown(model: CreateScreenModel) {
    val vm = LocalCreateViewModel.current
    var expanded by remember { mutableStateOf(false) }
    val nav = LocalNavController.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Custom Domain:",
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Box {
                Button(onClick = { expanded = true }, enabled = !expanded) {
                    Text(model.domains[model.currentDomain])
                    Icon(Icons.Default.ExpandMore, "Show domain choices")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    model.domains.forEachIndexed { index, domain ->
                        DropdownMenuItem(
                            onClick = {
                                vm.onDomainChanged(index)
                                expanded = false
                            }
                        ) {
                            Text(domain)
                        }
                        Divider()
                    }
                    DropdownMenuItem(onClick = {
                        expanded = false
                        nav.navigate(SettingsScreen) // TODO: nav to domain management screen instead
                    }) {
                        Text("Add your custom domain")
                    }
                }
            }
        }
    }
}