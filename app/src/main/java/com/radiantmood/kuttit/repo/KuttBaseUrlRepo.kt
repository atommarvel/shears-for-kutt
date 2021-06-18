package com.radiantmood.kuttit.repo

import com.radiantmood.kuttit.util.getPrefDelegate

object KuttBaseUrlRepo {
    var baseUrl: String? by getPrefDelegate(prefs, "base_url", "kutt.it")
}