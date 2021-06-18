package com.radiantmood.kuttit.data

import kotlinx.serialization.Serializable

@Serializable
data class KuttLinkResponse(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val data: List<KuttLink>
)

@Serializable
data class KuttLink(
    val id: String, //uuid
    val created_at: String, //date-time
    val updated_at: String?, //date-time
    val address: String?,
    val link: String,
    val target: String,
    val description: String?,
    val password: Boolean,
    val visit_count: Int,
    val banned: Boolean,
    val expire_in: String?,
    val domain: String?,
)

@Serializable
data class KuttLinkCreation(
    val link: String,
)