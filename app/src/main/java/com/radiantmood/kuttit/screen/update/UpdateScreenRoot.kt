package com.radiantmood.kuttit.screen.update

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.radiantmood.kuttit.data.server.KuttLink

@Composable
fun UpdateScreenRoot(initialKuttLink: KuttLink) {
    // Scaffold + toolbar with back btn and save button
    // edit text fields for each property that can be manipulated
    // a viewmodel that will handle field values and call the update api
    Text(text = "Hello Update Screen Root. Time to update ${initialKuttLink.address}")
}