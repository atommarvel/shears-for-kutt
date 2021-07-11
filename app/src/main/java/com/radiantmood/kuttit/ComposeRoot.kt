package com.radiantmood.kuttit

import ComposableScreen.CreationScreen
import ComposableScreen.HomeScreen
import ComposableScreen.OnboardingScreen
import ComposableScreen.SettingsScreen
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.radiantmood.kuttit.nav.ConsumeExternallySharedText
import com.radiantmood.kuttit.nav.ExternallySharedTextViewModel
import com.radiantmood.kuttit.nav.composableScreen
import com.radiantmood.kuttit.nav.navigate
import com.radiantmood.kuttit.repo.SettingsRepo
import com.radiantmood.kuttit.ui.theme.KuttItTheme
import com.radiantmood.kuttit.util.snackbar.ConsumeSnackbarBuffer

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController") }
val LocalScaffoldState = compositionLocalOf<ScaffoldState> { error("No ScaffoldState") }
val LocalExternallySharedTextViewModel =
    compositionLocalOf<ExternallySharedTextViewModel> { error("No ExternallySharedTextViewModel") }

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
        LocalNavController provides rememberNavController(),
        LocalExternallySharedTextViewModel provides viewModel()
    ) {
        content()
    }
}

@Composable
fun Navigation() {
    val nav = LocalNavController.current
    NavHost(navController = nav, startDestination = HomeScreen.routeString()) {
        composableScreen(OnboardingScreen)
        composableScreen(HomeScreen)
        composableScreen(SettingsScreen)
        composableScreen(CreationScreen)
    }
}

@Composable
fun RootCommon() {
    ConsumeExternallySharedText()
    ConsumeSnackbarBuffer()
    if (SettingsRepo.onboardingFinished != true) {
        LocalNavController.current.navigate(OnboardingScreen.route())
    }
}