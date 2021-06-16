package com.radiantmood.kuttit.screen.creation

import com.radiantmood.kuttit.data.FinishedModelContainer

data class CreationScreenModel(
    val targetUrl: String,
    val currentDomain: Int,
    val domains: List<String>,
    val path: String,
    val password: String,
    val expires: String,
    val description: String,
    val fieldsEnabled: Boolean,
) : FinishedModelContainer<CreationScreenModel>()