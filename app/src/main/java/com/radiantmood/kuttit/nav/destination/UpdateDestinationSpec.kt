package com.radiantmood.kuttit.nav.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.screen.update.UpdateScreenRoot
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URLDecoder

object UpdateDestinationSpec : DestinationSpec {
    override val baseRoute = "update"

    const val KUTT_LINK_KEY = "kuttLink"

    override val screenRoot: ScreenRoot = { backStackEntry ->
        val serializedKuttLink = checkNotNull(backStackEntry.arguments?.getString(KUTT_LINK_KEY)?.takeIf { it.isNotEmpty() }) {
            "Update Screen cannot be rendered if an item to update doesn't exist."
        }
        val decodedKuttLink = URLDecoder.decode(serializedKuttLink, "utf-8")
        val kuttLink = Json.decodeFromString<KuttLink>(decodedKuttLink)
        UpdateScreenRoot(kuttLink)
    }

    override val requiredArgs: List<NamedNavArgument> = listOf(
        navArgument(KUTT_LINK_KEY) {
            this.defaultValue = ""
            this.type = NavType.StringType
        }
    )
}