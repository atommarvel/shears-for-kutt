package com.radiantmood.kuttit.screen.create

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel

private val LocalCreateViewModel =
    compositionLocalOf<CreateViewModel> { error("No CreateViewModel") }

@Composable
fun CreateScreenRoot() {
    val vm: CreateViewModel = viewModel()
    CompositionLocalProvider(
        LocalCreateViewModel provides vm
    ) {
        CreateScreen()
    }
}

@Composable
fun CreateScreen() {
    Text("Hello World")
}