package com.radiantmood.kuttit.data.server

import kotlinx.serialization.Serializable

@Serializable
data class GetLinksResponse(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val data: List<KuttLink>,
)

