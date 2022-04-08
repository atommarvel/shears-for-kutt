package com.radiantmood.kuttit.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.radiantmood.kuttit.nav.destination.DestinationSpec

fun NavGraphBuilder.composable(spec: DestinationSpec, navRouteFactory: NavRouteFactory) {
    this.composable(
        navRouteFactory.registerNavRoute(spec).toString(),
        spec.optionalArgs,
        spec.deepLinks,
        spec.screenRoot,
    )
}

fun NavController.navTo(
    route: NavRoute,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(route.toString(), navOptions, navigatorExtras)
}