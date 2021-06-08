package com.radiantmood.kuttit.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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