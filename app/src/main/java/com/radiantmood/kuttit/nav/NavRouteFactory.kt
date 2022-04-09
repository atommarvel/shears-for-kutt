package com.radiantmood.kuttit.nav

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.nav.destination.CreationDestinationSpec
import com.radiantmood.kuttit.nav.destination.DestinationSpec
import com.radiantmood.kuttit.nav.destination.HomeDestinationSpec
import com.radiantmood.kuttit.nav.destination.OnboardingDestinationSpec
import com.radiantmood.kuttit.nav.destination.SettingsDestinationSpec
import com.radiantmood.kuttit.nav.destination.UpdateDestinationSpec
import com.radiantmood.kuttit.util.bundleOfNotNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import javax.inject.Inject

/**
 * Provides [NavRoute] for each implementation of [DestinationSpec]
 */
interface NavRouteFactory {
    fun creationDestinationNavRoute(target: String? = null): NavRoute
    fun updateDestinationNavRoute(kuttLink: KuttLink): NavRoute
    fun homeDestinationNavRoute(): NavRoute
    fun onboardingDestinationNavRoute(): NavRoute
    fun settingsDestinationNavRoute(): NavRoute
    fun registerNavRoute(spec: DestinationSpec): NavRoute
}

class NavRouteFactoryImpl @Inject constructor() : NavRouteFactory {

    override fun updateDestinationNavRoute(kuttLink: KuttLink): NavRoute = createNavRoute(
        spec = UpdateDestinationSpec,
        arguments = bundleOfNotNull(
            UpdateDestinationSpec.KUTT_LINK_KEY to URLEncoder.encode(Json.encodeToString(kuttLink), "utf-8")
        )
    )

    override fun creationDestinationNavRoute(target: String?): NavRoute = createNavRoute(
        spec = CreationDestinationSpec,
        arguments = bundleOfNotNull(CreationDestinationSpec.TARGET_KEY to target),
    )

    private val homeNav by lazy { createNavRoute(spec = HomeDestinationSpec) }
    override fun homeDestinationNavRoute(): NavRoute = homeNav

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