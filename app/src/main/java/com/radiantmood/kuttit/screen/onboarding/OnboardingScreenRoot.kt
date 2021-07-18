package com.radiantmood.kuttit.screen.onboarding

import ComposableScreen.HomeScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.repo.SettingsRepo
import com.radiantmood.kuttit.ui.component.KuttTopAppBar

val LocalOnboardingViewModel =
    compositionLocalOf<OnboardingViewModel> { error("No OnboardingViewModel") }

@Composable
fun OnboardingScreenRoot() {
    val vm: OnboardingViewModel = viewModel()
    val scaffoldState = rememberScaffoldState()
    CompositionLocalProvider(
        LocalOnboardingViewModel provides vm,
        LocalScaffoldState provides scaffoldState
    ) {
        OnboardingScreen()
    }
}

@Composable
fun OnboardingScreen() {
    Scaffold(
        topBar = { OnboardingAppBar() },
    ) {
        OnboardingBody()
    }
}

@Composable
fun OnboardingAppBar() {
    KuttTopAppBar(
        title = "Onboarding",
        allowBack = false,
        actions = { SkipAppBarAction() }
    )
}

@Composable
fun SkipAppBarAction() {
    val nav = LocalNavController.current
    Text(
        text = "Skip",
        modifier = Modifier
            .clickable { finishOnboarding(nav) }
            .padding(8.dp)
    )
}

fun finishOnboarding(nav: NavHostController) {
    SettingsRepo.onboardingFinished = true
    nav.popBackStack(HomeScreen.routeString(), false)
}

@Composable
fun OnboardingBody() {
    val vm = LocalOnboardingViewModel.current
    OnboardingPager { page, screenModel ->
        when (page) {
            0 -> ApiKeyOnboarding(screenModel, setApiKey = vm::updateApiKey)
            1 -> CrashlyticsOnboarding(screenModel)
            else -> throw IllegalStateException("unable to handle onboarding page $page")
        }
    }
}
