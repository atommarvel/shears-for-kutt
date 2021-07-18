package com.radiantmood.kuttit.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingBottomNav(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    currentPage: Int,
    finishOnboarding: () -> Unit,
) {
    val scope = rememberCoroutineScope()
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
            if (currentPage == 0) {
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
                        val index = currentPage + 1
                        if (index < onboardingPageCount) {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        } else {
                            finishOnboarding()
                        }
                    }
                    .align(Alignment.CenterEnd)
                    .padding(16.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val pagerState = rememberPagerState(pageCount = onboardingPageCount)
    LaunchedEffect(true) {
        pagerState.scrollToPage(1)
    }
    OnboardingBottomNav(
        pagerState = pagerState,
        currentPage = 1,
        finishOnboarding = {}
    )
}