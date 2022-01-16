package com.radiantmood.kuttit.nav.destination

import com.radiantmood.kuttit.screen.settings.SettingsScreenRoot

object SettingsDestinationSpec : DestinationSpec {
    override val baseRoute = "settings"
    override val screenRoot: ScreenRoot = { SettingsScreenRoot() }
}