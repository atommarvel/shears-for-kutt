package com.radiantmood.kuttit.screen.home

import com.radiantmood.kuttit.data.FinishedModelContainer
import com.radiantmood.kuttit.data.KuttLink

sealed class HomeScreenModel : FinishedModelContainer<HomeScreenModel>() {
    data class Content(
        val links: List<KuttLink>,
        val dialogLink: KuttLink? = null
    ) : HomeScreenModel()

    object ApiKeyMissing : HomeScreenModel()
}