package com.radiantmood.kuttit.nav

/**
 * Primarily used to make it easier to see where the route string is used.
 */
@JvmInline
value class NavRoute(private val value: String) {
    override fun toString(): String = value
}