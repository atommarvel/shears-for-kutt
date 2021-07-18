package com.radiantmood.kuttit.screen.creation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DomainDropdown(
    model: CreationScreenModel,
    onDomainChanged: (Int) -> Unit,
    navToDomainManagement: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Custom Domain:",
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Box {
                // TODO: why does this button not disable until after the first time interacting with it? is this a me issue or a compose issue?
                Button(onClick = { expanded = true }, enabled = !expanded && model.fieldsEnabled) {
                    Text(model.domains[model.currentDomain])
                    Icon(Icons.Default.ExpandMore, "Show domain choices")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    model.domains.forEachIndexed { index, domain ->
                        DropdownMenuItem(
                            onClick = {
                                onDomainChanged(index)
                                expanded = false
                            }
                        ) {
                            Text(domain)
                        }
                        Divider()
                    }
                    DropdownMenuItem(onClick = {
                        expanded = false
                        navToDomainManagement()
                    }) {
                        Text("Add your custom domain")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    DomainDropdown(
        model = creationScreenModelPreview,
        onDomainChanged = { },
        navToDomainManagement = { }
    )
}