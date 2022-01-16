package com.radiantmood.kuttit.nav.destination

import com.radiantmood.kuttit.screen.home.HomeScreenRoot

/**
 * The main destination.
 */
object HomeDestinationSpec : DestinationSpec {
    override val baseRoute = "home"
    override val screenRoot: ScreenRoot = { HomeScreenRoot() }
}