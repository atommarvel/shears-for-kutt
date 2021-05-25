package com.radiantmood.kuttit.ui.component

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
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.radiantmood.kuttit.LocalNavController
import com.radiantmood.kuttit.ui.theme.KuttItTheme

@Composable
fun KuttTopAppBar(title: String, actions: @Composable () -> Unit = {}) {
    Surface(
        modifier = Modifier.height(56.dp),
        color = MaterialTheme.colors.primarySurface
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBack()
            Text(
                text = title,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
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
            onClick = { navController.popBackStack() })
    }
}

@Composable
fun AppBarAction(imageVector: ImageVector, onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(imageVector, null) // TODO: null
    }
}

@Preview
@Composable
fun DefaultPreview() {
    KuttItTheme {
        CompositionLocalProvider(
            LocalNavController provides rememberNavController()
        ) {
            KuttTopAppBar(title = "Example")
        }
    }
}