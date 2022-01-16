package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalRootViewModel
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.data.local.LoadingModelContainer
import com.radiantmood.kuttit.nav.destination.NavRouteFactory
import com.radiantmood.kuttit.ui.component.KuttTopAppBar

val LocalOnboardingViewModel =
    compositionLocalOf<OnboardingViewModel> { error("No OnboardingViewModel") }

@Composable
fun OnboardingScreenRoot() {
    val vm: OnboardingViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    CompositionLocalProvider(
        LocalOnboardingViewModel provides vm,
        LocalScaffoldState provides scaffoldState
    ) {
        OnboardingScreen {
            OnboardingBody()
        }
    }
}

@Composable
fun OnboardingScreen(content: @Composable () -> Unit) {
    Scaffold(
        topBar = { OnboardingAppBar() },
    ) {
        content()
    }
}

@Composable
fun OnboardingAppBar() {
    KuttTopAppBar(
        title = stringResource(R.string.onboarding),
        allowBack = false,
        actions = { SkipAppBarAction() }
    )
}

@Composable
fun SkipAppBarAction() {
    val nav = LocalNavController.current
    val vm = LocalOnboardingViewModel.current
    val rvm = LocalRootViewModel.current
    Text(
        text = stringResource(R.string.nav_skip),
        modifier = Modifier
            .clickable { finishOnboarding(vm, rvm, nav) }
            .padding(8.dp)
    )
}

fun finishOnboarding(
    vm: OnboardingViewModel,
    routeFactory: NavRouteFactory,
    nav: NavHostController,
) {
    vm.setOnboardingFinished(true)
    nav.popBackStack(routeFactory.homeDestinationNavRoute().toString(), false)
}

@Composable
fun OnboardingBody() {
    val vm = LocalOnboardingViewModel.current
    val rvm = LocalRootViewModel.current
    val nav = LocalNavController.current
    val modelContainer by vm.screenModel.observeAsState(LoadingModelContainer())
    OnboardingPager(
        modelContainer = modelContainer,
        finishOnboarding = { finishOnboarding(vm, rvm, nav) }
    ) { page, screenModel ->
        when (page) {
            0 -> ApiKeyOnboarding(screenModel, setApiKey = vm::updateApiKey)
            1 -> CrashlyticsOnboarding(screenModel, vm::updateCrashlytics)
            else -> throw IllegalStateException("unable to handle onboarding page $page")
        }
    }
}

