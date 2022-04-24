package com.radiantmood.kuttit.screen.update

import com.radiantmood.kuttit.data.local.FinishedModelContainer

data class UpdateScreenModel(
    val targetUrl: String,
    val path: String,
    val expires: String,
    val description: String,
    val fieldsEnabled: Boolean,
) : FinishedModelContainer<UpdateScreenModel>()