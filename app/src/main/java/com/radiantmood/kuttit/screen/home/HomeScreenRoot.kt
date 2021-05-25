package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.SettingsScreen
import com.radiantmood.kuttit.navigate

@Composable
fun HomeScreenRoot() {
    val nav = LocalNavController.current
    Column {
        Text(text = "Hello home screen")
        Button(onClick = { nav.navigate(SettingsScreen) }) {
            Text("Go to settings screen")
        }
    }
}