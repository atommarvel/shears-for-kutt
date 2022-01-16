package com.radiantmood.kuttit.data.server

import kotlinx.serialization.Serializable

/**
 * @param link the shortened kutt url
 */
@Serializable
data class PostLinkResponse(
    val link: String,
)