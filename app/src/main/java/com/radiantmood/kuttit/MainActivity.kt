package com.radiantmood.kuttit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radiantmood.kuttit.ui.theme.KuttItTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KuttItTheme {
                Links()
            }
        }
    }
}

@Composable
fun Links() {
    val vm: MainViewModel = viewModel()
    val apiKey by vm.apiKeyLiveData.observeAsState()
    val response by vm.linksLiveData.observeAsState()

    Column {
        TextField(
            value = apiKey.orEmpty(),
            onValueChange = { vm.updateApiKey(it) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { vm.getLinks() }) {
            Text("Get Links")
        }
        response?.let {
            it.data.forEach { link ->
                Text("${link.link} → ${link.target}")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KuttItTheme {
        Links()
    }
}