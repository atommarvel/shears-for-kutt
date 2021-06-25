package com.radiantmood.kuttit

import ComposableScreen.CreationScreen
import ComposableScreen.HomeScreen
import ComposableScreen.SettingsScreen
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.radiantmood.kuttit.nav.ExternallySharedTextViewModel
import com.radiantmood.kuttit.nav.composableScreen
import com.radiantmood.kuttit.nav.navigate
import com.radiantmood.kuttit.ui.theme.KuttItTheme

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController") }
val LocalScaffoldState = compositionLocalOf<ScaffoldState> { error("No ScaffoldState") }

@Composable
fun ComposeRoot() {
    KuttItTheme {
        Surface {
            RootLocalProvider {
                Navigation()
            }
        }
    }
}

@Composable
fun RootLocalProvider(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController()
    ) {
        content()
    }
}

@Composable
fun Navigation() {
    val nav = LocalNavController.current
    NavHost(navController = nav, startDestination = HomeScreen.route().toString()) {
        composableScreen(HomeScreen)
        composableScreen(SettingsScreen)
        composableScreen(CreationScreen)
    }
    consumeExternallySharedText()
}

@Composable
fun consumeExternallySharedText() {
    val vm: ExternallySharedTextViewModel = viewModel()
    val sharedText by vm.sharedText.observeAsState()
    val nav = LocalNavController.current
    sharedText?.getContentIfNotHandled()?.let {
        nav.navigate(CreationScreen.route(it))
    }
}