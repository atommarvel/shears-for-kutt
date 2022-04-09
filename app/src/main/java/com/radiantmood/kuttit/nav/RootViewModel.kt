package com.radiantmood.kuttit.nav

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.LocalRootViewModel
import com.radiantmood.kuttit.repo.OnboardingStatusSource
import com.radiantmood.kuttit.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * I'm not sure about a way to cleanly provide services to Compose yet, so I'm providing them via
 *  this ViewModel.
 *  TODO: check how the compose sample apps do this
 */
@HiltViewModel
class RootViewModel @Inject constructor(
    private val onboardingStatusSource: OnboardingStatusSource,
    private val navRouteFactory: NavRouteFactory,
) : ViewModel(),
    NavRouteFactory by navRouteFactory {

    /**
     * The [Event]s made available at [sharedText] contain text that was shared to this app via
     *  the following intent actions.
     * - [Intent.ACTION_SEND]
     * - [Intent.ACTION_SEND_MULTIPLE]
     * - [Intent.ACTION_PROCESS_TEXT]
     */
    private val _sharedText = MutableLiveData<Event<String>>()
    val sharedText: LiveData<Event<String>> get() = _sharedText

    fun consumeIntent(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            ?: intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        sharedText?.let { _sharedText.postValue(Event(it.toString())) }
    }

    fun isOnboardingFinished() = onboardingStatusSource.onboardingFinished
}

/**
 * React to receiving urls to shorten within Compose.
 * TODO: move to the compose root level
 */
@Composable
fun ConsumeExternallySharedText() {
    val rvm = LocalRootViewModel.current
    val sharedText by rvm.sharedText.observeAsState()
    val nav = LocalNavController.current
    sharedText?.getContentIfNotHandled()?.let {
        nav.navTo(rvm.creationDestinationNavRoute(it))
    }
}