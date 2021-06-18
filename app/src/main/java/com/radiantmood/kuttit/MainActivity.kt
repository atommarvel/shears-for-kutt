package com.radiantmood.kuttit

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.Navigation.findNavController

class MainActivity : ComponentActivity() {

    private val navController by lazy {
        findNavController(
            window.decorView.findViewById<ViewGroup>(android.R.id.content)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRoot()
        }
    }
}