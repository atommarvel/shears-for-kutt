package com.radiantmood.kuttit.data.server

import kotlinx.serialization.Serializable

/**
 * @param link the shortened kutt url
 * @param target the url that [link] redirects to
 */
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