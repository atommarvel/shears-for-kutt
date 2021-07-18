package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.data.LoadingModelContainer
import com.radiantmood.kuttit.util.ModelContainerContent

/**
 * See the when expression in HorizontalPager's content param for
 *  what page maps to what onboarding step.
 */
const val onboardingPageCount = 2

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(content: @Composable (Int, OnboardingScreenModel) -> Unit) {
    val nav = LocalNavController.current
    val vm = LocalOnboardingViewModel.current
    val modelContainer by vm.screenModel.observeAsState(LoadingModelContainer()) // TODO: nah
    val pagerState = rememberPagerState(onboardingPageCount)
    ModelContainerContent(modelContainer) { screenModel ->
        Column {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    content(page, screenModel)
                }
            }
            OnboardingBottomNav(
                pagerState = pagerState,
                currentPage = pagerState.currentPage,
                finishOnboarding = { finishOnboarding(nav) }
            )
        }
    }
}