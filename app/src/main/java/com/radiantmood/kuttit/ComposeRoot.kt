package com.radiantmood.kuttit

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.radiantmood.kuttit.ui.theme.KuttItTheme

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController") }

@Composable
fun ComposeRoot() {
    KuttItTheme {
        Surface {
            RootLocalProvider {
                Navigation()
            }
        }
    }
}

@Composable
fun RootLocalProvider(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController()
    ) {
        content()
    }
}

@Composable
fun Navigation() {
    NavHost(navController = LocalNavController.current, startDestination = HomeScreen.route) {
        composableScreen(HomeScreen)
        composableScreen(SettingsScreen)
        composableScreen(CreateScreen)
    }
}