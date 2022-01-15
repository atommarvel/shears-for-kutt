package com.radiantmood.kuttit
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.radiantmood.kuttit.nav.RootViewModel
import com.radiantmood.kuttit.util.AppUpdater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val rootViewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRoot()
        }
    }

    override fun onResume() {
        super.onResume()
        consumeIntent()
        AppUpdater.onResume(this)
    }

    // TODO#1knqt14: Switch to contracts once the in-app updates library switches
    @Suppress("Deprecation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        AppUpdater.onActivityResult(this, requestCode, resultCode)
    }

    override fun onNewIntent(intent: Intent?) = consumeIntent()

    private fun consumeIntent() {
        intent?.let {
            intent = null
            rootViewModel.consumeIntent(it)
        }
    }
}