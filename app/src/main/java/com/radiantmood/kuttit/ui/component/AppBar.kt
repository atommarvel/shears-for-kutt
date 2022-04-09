package com.radiantmood.kuttit.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.ui.theme.KuttItTheme
import com.radiantmood.kuttit.ui.theme.primaryGradient

@Composable
fun KuttTopAppBar(
    title: String,
    allowBack: Boolean = true,
    actions: @Composable () -> Unit = {},
) {
    Surface(color = MaterialTheme.colors.primary) {
        Row(
            modifier = Modifier
                .background(primaryGradient)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (allowBack) {
                NavBack()
            }
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
            actions()
        }
    }
}

@Composable
fun NavBack() {
    val navController = LocalNavController.current
    if (navController.previousBackStackEntry != null) {
        AppBarAction(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Go back",
            onClick = { navController.popBackStack() })
    }
}

@Composable
fun AppBarAction(imageVector: ImageVector, contentDescription: String, onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(imageVector, contentDescription)
    }
}

@Preview()
@Composable
private fun DefaultPreview() {
    KuttItTheme(false) {
        CompositionLocalProvider(
            LocalNavController provides rememberNavController()
        ) {
            KuttTopAppBar(title = "Example")
        }
    }
}