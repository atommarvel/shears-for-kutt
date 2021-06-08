package com.radiantmood.kuttit.screen.create

import com.radiantmood.kuttit.data.FinishedModelContainer

data class CreateScreenModel(
    val targetUrl: String,
    val currentDomain: Int,
    val domains: List<String>,
    val path: String,
    val password: String,
    val expires: String,
    val description: String,
    val fieldsEnabled: Boolean
) : FinishedModelContainer<CreateScreenModel>()