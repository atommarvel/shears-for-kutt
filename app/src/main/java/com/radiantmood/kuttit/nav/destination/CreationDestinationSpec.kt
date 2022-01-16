package com.radiantmood.kuttit.nav.destination

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.radiantmood.kuttit.screen.creation.CreationScreenRoot

/**
 * Shortened link creation destination.
 */
object CreationDestinationSpec : DestinationSpec {
    override val baseRoute = "creation"

    // key for the url to shorten
    const val TARGET_KEY = "target"

    override val screenRoot: ScreenRoot = { backStackEntry ->
        CreationScreenRoot(backStackEntry.arguments?.getString(TARGET_KEY).orEmpty())
    }

    override val optionalArgs = listOf(
        navArgument(TARGET_KEY) {
            this.defaultValue = ""
            this.type = NavType.StringType
        }
    )
}