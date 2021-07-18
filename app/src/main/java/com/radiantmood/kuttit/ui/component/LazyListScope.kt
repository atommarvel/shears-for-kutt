package com.radiantmood.kuttit.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.ui.Modifier

fun LazyListScope.LazyDivider(modifier: Modifier = Modifier) = item {
    Divider(modifier = modifier)
}

fun LazyListScope.LazySpacer(modifier: Modifier = Modifier) = item {
    Spacer(modifier = modifier)
}