package com.radiantmood.kuttit.screen.creation

import com.radiantmood.kuttit.dev.getDomainOrNull
import com.radiantmood.kuttit.repo.KuttUrlProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DomainsRepository @Inject constructor(
    private val kuttUrlProvider: KuttUrlProvider,
) {
    // TODO#zbbn26: get domains from the api
    fun getDomains(): List<String> = listOfNotNull(
        kuttUrlProvider.baseUrl,
        getDomainOrNull(),
    )
}