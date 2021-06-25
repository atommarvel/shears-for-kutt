package com.radiantmood.kuttit
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.radiantmood.kuttit.nav.ExternallySharedTextViewModel

class MainActivity : ComponentActivity() {

    private val externallySharedTextViewModel by viewModels<ExternallySharedTextViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRoot()
        }
    }

    override fun onResume() {
        super.onResume()
        consumeIntent()
    }

    override fun onNewIntent(intent: Intent?) = consumeIntent()

    private fun consumeIntent() {
        intent?.let {
            intent = null
            externallySharedTextViewModel.consumeIntent(it)
        }
    }
}