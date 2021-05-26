package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.SettingsScreen
import com.radiantmood.kuttit.navigate
import com.radiantmood.kuttit.ui.component.AppBarAction
import com.radiantmood.kuttit.ui.component.KuttTopAppBar

private val LocalHomeViewModel =
    compositionLocalOf<HomeViewModel> { error("No HomeViewModel") }

@Composable
fun HomeScreenRoot() {
    val vm: HomeViewModel = viewModel()
    CompositionLocalProvider(
        LocalHomeViewModel provides vm
    ) {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    Scaffold(
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
    Text("Hello home")
}

@Composable
fun Links() {
    val vm: HomeViewModel = viewModel()
    val apiKey by vm.apiKeyLiveData.observeAsState()
    val response by vm.linksLiveData.observeAsState()
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = apiKey.orEmpty(),
            onValueChange = { vm.updateApiKey(it) },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        "show/hide key"
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { vm.getLinks() }) {
            Text("Get Links")
        }
        response?.let {
            it.data.forEach { link ->
                Text("${link.link} → ${link.target}")
            }
        }
    }
}