package com.radiantmood.kuttit.screen.creation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.ui.component.LazySpacer

@Composable
fun CreationForm(model: CreationScreenModel, actions: CreationActions) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TextField(
                value = model.targetUrl,
                onValueChange = actions::onTargetUrlChanged,
                label = { Text(stringResource(id = R.string.url_label)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = model.fieldsEnabled,
            )
        }
        LazySpacer(Modifier.height(32.dp))
        item {
            Text(stringResource(R.string.advanced_options), modifier = Modifier.fillMaxWidth())
        }
        LazySpacer(Modifier.height(8.dp))
        item {
            DomainDropdown(
                model = model,
                onDomainChanged = actions::onDomainChanged,
                navToDomainManagement = actions::navToDomainManagement
            )
        }
        LazySpacer(Modifier.height(16.dp))
        item {
            TextField(
                value = model.path,
                onValueChange = actions::onPathChanged,
                label = { Text(stringResource(R.string.custom_path)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = model.fieldsEnabled,
            )
        }
        LazySpacer(Modifier.height(16.dp))
        item {
            TextField(
                value = model.password,
                onValueChange = actions::onPasswordChanged,
                label = { Text(stringResource(R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = model.fieldsEnabled,
            )
        }
        LazySpacer(Modifier.height(16.dp))
        item {
            TextField(
                value = model.expires,
                onValueChange = actions::onExpiresChanged,
                label = { Text(stringResource(R.string.expire_in)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.expire_in_placeholder)) },
                enabled = model.fieldsEnabled,
            )
        }
        LazySpacer(Modifier.height(16.dp))
        item {
            TextField(
                value = model.description,
                onValueChange = actions::onDescriptionChanged,
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = model.fieldsEnabled,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    CreationForm(model = creationScreenModelPreview, actions = CreationActionsPreview())
}