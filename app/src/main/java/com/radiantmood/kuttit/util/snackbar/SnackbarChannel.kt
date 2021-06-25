package com.radiantmood.kuttit.util.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

val SnackbarChannel = Channel<Event<String>>()

suspend fun Channel<Event<String>>.post(message: String) {
    send(Event(message))
}

fun ViewModel.postSnackbar(message: String) = viewModelScope.postSnackbar(message)

fun CoroutineScope.postSnackbar(message: String) = launch {
    SnackbarChannel.post(message)
}

fun CoroutineScope.postSnackbar(e: Throwable) = e.localizedMessage?.let {
    this.postSnackbar(it)
}

/**
 * Use [NavHostController.saveState] to pass snackbar messages to previous backstack entries
 * TODO: support sending to future backstack entries via arguments?
 */
private const val SNACKBAR_BUFFER_KEY = "snackbar_message"

fun NavHostController.postSnackbarBuffer(message: String) {
    previousBackStackEntry?.savedStateHandle?.set(SNACKBAR_BUFFER_KEY, message)
}

@Composable
fun ConsumeSnackbarBuffer() {
    val nav = LocalNavController.current
    val scope = rememberCoroutineScope()
    nav.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String?>(SNACKBAR_BUFFER_KEY)
        ?.observeAsState<String?>()?.value?.let { message ->
            nav.currentBackStackEntry?.savedStateHandle?.set(SNACKBAR_BUFFER_KEY, null)
            scope.postSnackbar(message)
        }
}