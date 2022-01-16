package com.radiantmood.kuttit.data.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param path the custom path the kutt url should use. Leave null to let Kutt backend make one up.
 * @param targetUrl the url that [link] redirects to
 * @param domain to specify a user-owned domain to use besides kutt.it
 */
@Serializable
data class PostLinkBody(
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