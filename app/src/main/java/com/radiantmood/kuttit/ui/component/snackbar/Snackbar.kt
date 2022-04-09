package com.radiantmood.kuttit.ui.component.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.radiantmood.kuttit.LocalScaffoldState
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
fun KuttSnackbar() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalScaffoldState.current.snackbarHostState
    val snackbarMessage by SnackbarChannel.receiveAsFlow().collectAsState(initial = null)
    snackbarMessage?.getContentIfNotHandled()?.let {
        scope.launch {
            snackbarHostState.showSnackbar(it)
        }
    }
}