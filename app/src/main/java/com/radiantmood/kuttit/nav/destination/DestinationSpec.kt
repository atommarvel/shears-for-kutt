package com.radiantmood.kuttit.nav.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

/**
 * An attempt to wrap the string routes in a more concrete
 *  representation of each of the app's destinations.
 */
interface DestinationSpec {
    val baseRoute: String
    val screenRoot: ScreenRoot
    val requiredArgs: List<NamedNavArgument> get() = emptyList()
    val optionalArgs: List<NamedNavArgument> get() = emptyList()
    val deepLinks: List<NavDeepLink> get() = emptyList()
}