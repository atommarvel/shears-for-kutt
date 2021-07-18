package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.radiantmood.kuttit.data.ModelContainer
import com.radiantmood.kuttit.util.ModelContainerContent

/**
 * See the when expression in HorizontalPager's content param for
 *  what page maps to what onboarding step.
 */
const val onboardingPageCount = 2

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(
    modelContainer: ModelContainer<OnboardingScreenModel>,
    finishOnboarding: () -> Unit,
    content: @Composable (Int, OnboardingScreenModel) -> Unit,
) {
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
                finishOnboarding = finishOnboarding
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    OnboardingPager(
        modelContainer = OnboardingScreenModel(
            apiKey = "abcdefghijklmnopqrstuvwxyz",
            isCrashlyticsEnabled = true
        ),
        finishOnboarding = {}
    ) { page, _ ->
        Text("Hello Page $page!")
    }
}