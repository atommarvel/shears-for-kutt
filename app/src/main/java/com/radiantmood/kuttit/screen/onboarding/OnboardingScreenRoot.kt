package com.radiantmood.kuttit.screen.onboarding

import ComposableScreen.HomeScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalScaffoldState
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.repo.SettingsRepo
import com.radiantmood.kuttit.ui.component.KuttTopAppBar
import com.radiantmood.kuttit.util.ModelContainerContent
import kotlinx.coroutines.launch

private val LocalOnboardingViewModel =
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

const val onboardingPageCount = 2

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingBody() {
    val vm = LocalOnboardingViewModel.current
    val modelContainer by vm.screenModel.observeAsState(LoadingModelContainer())
    val pagerState = rememberPagerState(onboardingPageCount)
    ModelContainerContent(modelContainer) { screenModel ->
        Column {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> ApiKeyOnboarding(screenModel.apiKey)
                    1 -> CrashlyticsOnboarding(screenModel.isCrashlyticsEnabled)
                    else -> throw IllegalStateException("unable to handle onboarding page $page")
                }
            }
            PagerNavRow(pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerNavRow(modifier: Modifier = Modifier, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val nav = LocalNavController.current
    Row(modifier = modifier.padding(8.dp)) {
        Box(Modifier
            .align(Alignment.CenterVertically)
            .weight(1f)) {
            Text("Back", modifier = Modifier
                .clickable {
                    val index = pagerState.currentPage - 1
                    if (index >= 0) {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    }
                }
                .padding(16.dp)
            )
            // Hide back button by covering it up to keep things lined up
            if (pagerState.currentPage == 0) {
                Box(Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colors.surface))
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp),
        )
        val forwardLabel =
            if (pagerState.currentPage == (onboardingPageCount - 1)) "Finish" else "Next"
        Box(Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)) {
            Text(
                text = forwardLabel,
                modifier = Modifier
                    .clickable {
                        val index = pagerState.currentPage + 1
                        if (index < onboardingPageCount) {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        } else {
                            finishOnboarding(nav)
                        }
                    }
                    .align(Alignment.CenterEnd)
                    .padding(16.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun ApiKeyOnboarding(apiKey: String?) {
    Column(Modifier.fillMaxWidth()) {
        Text("apiKey:")
        Text(text = apiKey.orEmpty())
    }
}

@Composable
fun CrashlyticsOnboarding(crashlyticsEnabled: Boolean) {
    Column(Modifier.fillMaxWidth()) {
        Text("CrashlyticsOnboarding\nOpted in: $crashlyticsEnabled")
    }
}

