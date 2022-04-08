package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.R
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.dev.preview
import com.radiantmood.kuttit.ui.component.PlatformDialog

@Composable
fun LinkDialog(
    link: KuttLink?,
    copyToClipboard: (String) -> Unit,
    updateLink: (KuttLink) -> Unit,
    deleteLink: (KuttLink) -> Unit,
    closeDialog: () -> Unit,
) {
    link?.let {
        PlatformDialog(onDismissRequest = closeDialog) {
            LinkDialogContent(link, copyToClipboard, closeDialog, updateLink, deleteLink)
        }
    }
}

@Composable
private fun LinkDialogContent(
    link: KuttLink,
    copyToClipboard: (String) -> Unit,
    closeDialog: () -> Unit,
    updateLink: (KuttLink) -> Unit,
    deleteLink: (KuttLink) -> Unit,
) {
    Card {
        val textMod = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        Column {
            Text(
                text = link.link,
                modifier = textMod,
                textAlign = TextAlign.Center
            )
            CopyActionItem(textMod, link, copyToClipboard, closeDialog)
            Divider()
            UpdateActionItem(textMod, link, updateLink, closeDialog)
            Divider()
            DeleteActionItem(textMod, link, deleteLink, closeDialog)
            // TODO#zbbnrk: View QR Code
            // TODO#zbbnqj: View Stats
        }
    }
}

@Composable
private fun DeleteActionItem(
    modifier: Modifier,
    link: KuttLink,
    deleteLink: (KuttLink) -> Unit,
    closeDialog: () -> Unit,
) {
    Text(
        text = stringResource(R.string.dialog_delete),
        modifier = Modifier
            .clickable {
                deleteLink(link)
                closeDialog()
            }
            .then(modifier),
        color = Color.Red
    )
}

@Composable
private fun UpdateActionItem(
    modifier: Modifier,
    link: KuttLink,
    updateLink: (KuttLink) -> Unit,
    closeDialog: () -> Unit,
) {
    Text(
        text = stringResource(R.string.dialog_update),
        modifier = Modifier
            .clickable {
                updateLink(link)
                closeDialog()
            }
            .then(modifier)
    )
}

@Composable
private fun CopyActionItem(
    modifier: Modifier,
    link: KuttLink,
    copyToClipboard: (String) -> Unit,
    closeDialog: () -> Unit,
) {
    Text(
        text = stringResource(R.string.dialog_copy),
        modifier = Modifier
            .clickable {
                copyToClipboard(link.link)
                closeDialog()
            }
            .then(modifier)
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    val link = KuttLink.preview()
    LinkDialogContent(
        link = link,
        copyToClipboard = {},
        updateLink = {},
        deleteLink = {},
        closeDialog = {},
    )
}