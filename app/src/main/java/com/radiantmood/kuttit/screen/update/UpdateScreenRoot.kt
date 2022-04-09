package com.radiantmood.kuttit.screen.update

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.radiantmood.kuttit.data.server.KuttLink

@Composable
fun UpdateScreenRoot(initialKuttLink: KuttLink) {
    Text(text = "Hello Update Screen Root. Time to update ${initialKuttLink.address}")
}