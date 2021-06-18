package com.radiantmood.kuttit

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.radiantmood.kuttit.ui.theme.KuttItTheme

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController") }
val LocalScaffoldState = compositionLocalOf<ScaffoldState> { error("No ScaffoldState") }

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
    val context = LocalContext.current
    val nav = LocalNavController.current
    NavHost(navController = nav, startDestination = HomeScreen.route().toString()) {
        composableScreen(HomeScreen)
        composableScreen(SettingsScreen)
        composableScreen(CreationScreen)
    }
    // TODO: convert to a flow to handle new intents as they come in?
    SideEffect {
        (context as? Activity)?.consumeIntent(nav)
    }
}

fun Activity.consumeIntent(nav: NavHostController) {
    try {
        intent?.let { currentIntent ->
            val sharedText = currentIntent.getStringExtra(Intent.EXTRA_TEXT)
                ?: currentIntent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
            intent = null
            sharedText?.let { nav.navigate(CreationScreen.route(it.toString())) }
        }
    } catch (e: Exception) {
        Log.e("araiff", "${e.message}")
    }
}