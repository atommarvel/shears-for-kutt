package com.radiantmood.kuttit.nav.destination

import com.radiantmood.kuttit.screen.onboarding.OnboardingScreenRoot

object OnboardingDestinationSpec : DestinationSpec {
    override val baseRoute = "onboarding"
    override val screenRoot: ScreenRoot = { OnboardingScreenRoot() }
}