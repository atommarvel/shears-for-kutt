package com.radiantmood.kuttit

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
import com.radiantmood.kuttit.nav.RootViewModel
import com.radiantmood.kuttit.nav.composable
import com.radiantmood.kuttit.nav.destination.CreationDestinationSpec
import com.radiantmood.kuttit.nav.destination.HomeDestinationSpec
import com.radiantmood.kuttit.nav.destination.OnboardingDestinationSpec
import com.radiantmood.kuttit.nav.destination.SettingsDestinationSpec
import com.radiantmood.kuttit.nav.navTo
import com.radiantmood.kuttit.ui.component.snackbar.ConsumeSnackbarBuffer
import com.radiantmood.kuttit.ui.theme.KuttItTheme

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController") }
val LocalScaffoldState = compositionLocalOf<ScaffoldState> { error("No ScaffoldState") }
val LocalRootViewModel = compositionLocalOf<RootViewModel> { error("No RootViewModel") }

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
fun RootLocalProvider(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController(),
        LocalRootViewModel provides viewModel(),
    ) {
        content()
    }
}

@Composable
fun Navigation() {
    val nav = LocalNavController.current
    val rvm = LocalRootViewModel.current
    NavHost(navController = nav, startDestination = rvm.homeDestinationNavRoute().toString()) {
        composable(OnboardingDestinationSpec, rvm)
        composable(HomeDestinationSpec, rvm)
        composable(SettingsDestinationSpec, rvm)
        composable(CreationDestinationSpec, rvm)
    }
}

/**
 * TODO: move shared text and onboarding to the compose root level
 */
@Composable
fun RootCommon() {
    ConsumeExternallySharedText()
    ConsumeSnackbarBuffer()
    if (!LocalRootViewModel.current.isOnboardingFinished()) {
        val rvm = LocalRootViewModel.current
        // TODO: wrap nav controller in a NavigationManager
        LocalNavController.current.navTo(rvm.onboardingDestinationNavRoute())
    }
}