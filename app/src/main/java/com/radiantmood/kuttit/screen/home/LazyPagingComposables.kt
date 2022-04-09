package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.ui.component.snackbar.postSnackbar
import com.radiantmood.kuttit.util.LoadingScreen

fun lazyPagingFooter(
    lazyLinks: LazyPagingItems<KuttLink>,
    listScope: LazyListScope,
) = listScope.run {
    lazyLinks.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingScreen() }
            }
            loadState.append is LoadState.Loading -> {
                item { LoadingPagerFooter() }
            }
            loadState.refresh is LoadState.Error -> {
                val e = (loadState.refresh as LoadState.Error).error
                item { PagingSnackbarError(e) }
            }
            loadState.append is LoadState.Error -> {
                val e = (loadState.append as LoadState.Error).error
                item { PagingSnackbarError(e) }
            }
        }
    }
}

@Composable
fun PagingSnackbarError(e: Throwable) {
    LaunchedEffect(true) { postSnackbar(e) }
}

@Composable
private fun LoadingPagerFooter() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)) {
        CircularProgressIndicator()
    }
}