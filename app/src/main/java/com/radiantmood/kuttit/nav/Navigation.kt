package com.radiantmood.kuttit.nav

import ComposableScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableScreen(composableScreen: ComposableScreen) {
    composable(
        composableScreen.fullRoute,
        composableScreen.optionalArgs,
        composableScreen.deepLinks,
        composableScreen.content
    )
}

fun NavController.navigate(
    route: ComposableScreen.Route,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(route.toString(), navOptions, navigatorExtras)
}