package com.radiantmood.kuttit.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewKuttLinkBody(
    @SerialName("target")
    val targetUrl: String,
    val description: String?,
    @SerialName("expire_in")
    val expires: String?,
    val password: String?,
    @SerialName("customurl")
    val path: String?,
    val domain: String?,
    val reuse: Boolean? = false,
)