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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.radiantmood.kuttit.R
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
    val onBackClick = {
        val index = pagerState.currentPage - 1
        if (index >= 0) {
            scope.launch { pagerState.animateScrollToPage(index) }
        }
    }
    val onNextClick: () -> Unit = {
        val index = currentPage + 1
        if (index < onboardingPageCount) {
            scope.launch { pagerState.animateScrollToPage(index) }
        } else {
            finishOnboarding()
        }
    }

    Row(modifier = modifier.padding(8.dp)) {
        BackNavButton(
            modifier = Modifier.Companion
                .align(Alignment.CenterVertically)
                .weight(1f),
            onBackClick = onBackClick,
            currentPage = currentPage
        )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp),
        )
        NextNavButton(
            modifier = Modifier.Companion
                .weight(1f)
                .align(Alignment.CenterVertically),
            pagerState = pagerState,
            onNextClick = onNextClick
        )
    }
}

@Composable
private fun BackNavButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    currentPage: Int,
) {
    Box(modifier) {
        Text(
            text = stringResource(R.string.nav_back),
            modifier = Modifier
                .clickable(onClick = onBackClick)
                .padding(16.dp)
        )
        // Hide back button by covering it up to keep things lined up
        if (currentPage == 0) {
            Box(Modifier
                .matchParentSize()
                .background(MaterialTheme.colors.surface))
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun NextNavButton(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onNextClick: () -> Unit,
) {
    val forwardLabel =
        if (pagerState.currentPage == (onboardingPageCount - 1)) "Finish" else "Next"
    Box(modifier) {
        Text(
            text = forwardLabel,
            modifier = Modifier
                .clickable(onClick = onNextClick)
                .align(Alignment.CenterEnd)
                .padding(16.dp),
            textAlign = TextAlign.End
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val pagerState = rememberPagerState()
    LaunchedEffect(true) {
        pagerState.scrollToPage(1)
    }
    OnboardingBottomNav(
        pagerState = pagerState,
        currentPage = 1,
        finishOnboarding = {}
    )
}