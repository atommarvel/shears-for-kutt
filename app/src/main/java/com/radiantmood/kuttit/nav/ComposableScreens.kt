import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.radiantmood.kuttit.screen.creation.CreationScreenRoot
import com.radiantmood.kuttit.screen.home.HomeScreenRoot
import com.radiantmood.kuttit.screen.onboarding.OnboardingScreenRoot
import com.radiantmood.kuttit.screen.settings.SettingsScreenRoot

sealed class ComposableScreen(
    val rootRoute: String,
    val requiredArgs: List<NamedNavArgument> = emptyList(),
    val optionalArgs: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList(),
    val content: @Composable (NavBackStackEntry) -> Unit,
) {
    val fullRoute: String by lazy {
        buildString {
            append(rootRoute)
            requiredArgs.forEach {
                append("/")
                append(it.name.withBraces())
            }
            if (optionalArgs.isNotEmpty()) {
                append("?")
            }
            optionalArgs.forEachIndexed { index, arg ->
                append("${arg.name}=${arg.name.withBraces()}")
                if (index != optionalArgs.lastIndex) {
                    append("&")
                }
            }
        }
    }

    @Throws(Exception::class)
    fun route(b: Bundle? = null): Route = routeString(b).route()

    fun routeString(b: Bundle? = null) = buildString {
        val bundle = b ?: Bundle()
        append(rootRoute)
        requiredArgs.forEach { arg ->
            val path = checkNotNull(arg[bundle]) { "Required argument ${arg.name} missing" }
            append("/")
            append(path)
        }
        if (optionalArgs.isNotEmpty()) {
            append("?")
        }
        optionalArgs.forEachIndexed { index, arg ->
            arg[bundle]?.let {
                append("${arg.name}=${it}")
            }
            if (index != optionalArgs.lastIndex) {
                append("&")
            }
        }
    }

    private fun String.withBraces(): String = "{$this}"
    private fun String.route(): Route = Route(this)
    private operator fun NamedNavArgument.get(bundle: Bundle) = argument.type[bundle, name]

    @JvmInline
    value class Route(private val r: String) {
        override fun toString(): String = r
    }

    object HomeScreen : ComposableScreen(rootRoute = "home_screen", content = { HomeScreenRoot() })

    object SettingsScreen :
        ComposableScreen(rootRoute = "settings_screen", content = { SettingsScreenRoot() })

    object CreationScreen :
        ComposableScreen(
            rootRoute = "creation_screen",
            optionalArgs = listOf(
                navArgument("target") {
                    this.defaultValue = ""
                    this.type = NavType.StringType
                }
            ),
            content = { CreationScreenRoot(it.arguments?.getString("target").orEmpty()) }
        ) {
        fun route(target: String) = route(bundleOf("target" to target))
    }

    object OnboardingScreen :
        ComposableScreen(rootRoute = "onboarding_screen", content = { OnboardingScreenRoot() })
}