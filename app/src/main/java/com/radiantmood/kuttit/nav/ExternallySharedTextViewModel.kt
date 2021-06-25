package com.radiantmood.kuttit.nav

import ComposableScreen.CreationScreen
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.LocalExternallySharedTextViewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.util.Event

/**
 * The [Event]s made available at [sharedText] contain text that was shared to this app via the following intent actions.
 * - [Intent.ACTION_SEND]
 * - [Intent.ACTION_SEND_MULTIPLE]
 * - [Intent.ACTION_PROCESS_TEXT]
 */
class ExternallySharedTextViewModel : ViewModel() {
    private val _sharedText = MutableLiveData<Event<String>>()
    val sharedText: LiveData<Event<String>> get() = _sharedText

    fun consumeIntent(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            ?: intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        sharedText?.let { _sharedText.postValue(Event(it.toString())) }
    }
}

@Composable
fun ConsumeExternallySharedText() {
    val vm = LocalExternallySharedTextViewModel.current
    val sharedText by vm.sharedText.observeAsState()
    val nav = LocalNavController.current
    sharedText?.getContentIfNotHandled()?.let {
        nav.navigate(CreationScreen.route(it))
    }
}