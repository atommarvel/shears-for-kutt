package com.radiantmood.kuttit.screen.home

import androidx.paging.Pager
import com.radiantmood.kuttit.data.FinishedModelContainer
import com.radiantmood.kuttit.data.KuttLink

sealed class HomeScreenModel : FinishedModelContainer<HomeScreenModel>() {
    // TODO#1knqy2t: abstract the pager into a state object + list of links?
    data class Content(
        val kuttLinkPager: Pager<Int, KuttLink>,
        val dialogLink: KuttLink? = null,
    ) : HomeScreenModel()

    object ApiKeyMissing : HomeScreenModel()
}

data class KuttLinkModifier(val linkId: String, val isDeleting: Boolean = false)