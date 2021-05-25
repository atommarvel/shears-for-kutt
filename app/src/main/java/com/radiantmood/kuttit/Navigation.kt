package com.radiantmood.kuttit

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.composable
import com.radiantmood.kuttit.screen.home.HomeScreenRoot
import com.radiantmood.kuttit.screen.settings.SettingsScreenRoot

sealed class ComposableScreen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList(),
    val content: @Composable (NavBackStackEntry) -> Unit
)

//region Screens
object HomeScreen : ComposableScreen(route = "home_screen", content = { HomeScreenRoot() })

object SettingsScreen : ComposableScreen(route = "settings_screen", content = { SettingsScreenRoot() })
//endregion

//region ComposeableScreen helpers
fun NavGraphBuilder.composableScreen(composableScreen: ComposableScreen) {
    composable(composableScreen.route, composableScreen.arguments, composableScreen.deepLinks, composableScreen.content)
}

fun NavController.navigate(composableScreen: ComposableScreen, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(composableScreen.route, builder)
}
//endregion