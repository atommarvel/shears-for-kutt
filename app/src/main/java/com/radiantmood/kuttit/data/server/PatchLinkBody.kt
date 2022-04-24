package com.radiantmood.kuttit.data.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchLinkBody(
    @SerialName("target")
    val targetUrl: String,
    val description: String?,
    @SerialName("expire_in")
    val expires: String?,
    @SerialName("customurl")
    val path: String?,
)