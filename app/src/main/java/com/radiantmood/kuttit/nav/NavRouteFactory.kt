package com.radiantmood.kuttit.nav.destination

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import com.radiantmood.kuttit.nav.NavRoute
import com.radiantmood.kuttit.util.bundleOfNotNull
import javax.inject.Inject

/**
 * Provides [NavRoute] for each implementation of [DestinationSpec]
 */
interface NavRouteFactory {
    fun creationDestinationNavRoute(target: String? = null): NavRoute
    fun homeDestinationNavRoute(): NavRoute
    fun onboardingDestinationNavRoute(): NavRoute
    fun settingsDestinationNavRoute(): NavRoute
    fun registerNavRoute(spec: DestinationSpec): NavRoute
}

class NavRouteFactoryImpl @Inject constructor() : NavRouteFactory {
    override fun creationDestinationNavRoute(target: String?): NavRoute = createNavRoute(
        spec = CreationDestinationSpec,
        arguments = bundleOfNotNull(CreationDestinationSpec.TARGET_KEY to target),
    )

    override fun homeDestinationNavRoute(): NavRoute = createNavRoute(
        spec = HomeDestinationSpec
    )

    override fun onboardingDestinationNavRoute(): NavRoute = createNavRoute(
        spec = OnboardingDestinationSpec
    )

    override fun settingsDestinationNavRoute(): NavRoute = createNavRoute(
        spec = SettingsDestinationSpec
    )

    /**
     * @return [NavRoute] that can be provided to jetpack navigation to be registered as a route
     */
    override fun registerNavRoute(spec: DestinationSpec): NavRoute {
        val sb = StringBuilder(spec.baseRoute)
        spec.requiredArgs.forEach {
            sb.append("/")
            sb.append(it.name.surroundWithCurlyBraces())
        }
        if (spec.optionalArgs.isNotEmpty()) {
            sb.append("?")
        }
        spec.optionalArgs.forEachIndexed { index, arg ->
            sb.append("${arg.name}=${arg.name.surroundWithCurlyBraces()}")
            if (index != spec.optionalArgs.lastIndex) {
                sb.append("&")
            }
        }
        return sb.navRoute()
    }

    private fun createNavRoute(spec: DestinationSpec, arguments: Bundle? = null): NavRoute {
        val sb = StringBuilder(spec.baseRoute)
        val args = arguments ?: Bundle()
        spec.requiredArgs.forEach { reqArg ->
            val path = checkNotNull(args[reqArg]) { "Required argument ${reqArg.name} missing" }
            sb.append("/")
            sb.append(path)
        }
        if (spec.optionalArgs.isNotEmpty()) {
            sb.append("?")
        }
        spec.optionalArgs.forEachIndexed { index, optArg ->
            args[optArg]?.let {
                sb.append("${optArg.name}=${it}")
            }
            if (index != spec.optionalArgs.lastIndex) {
                sb.append("&")
            }
        }
        return sb.navRoute()
    }

    private fun String.surroundWithCurlyBraces(): String = "{$this}"
    private operator fun Bundle.get(arg: NamedNavArgument) = arg.argument.type[this, arg.name]
    private fun StringBuilder.navRoute() = NavRoute(toString())
}