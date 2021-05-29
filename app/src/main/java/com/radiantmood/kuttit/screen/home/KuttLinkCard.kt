package com.radiantmood.kuttit.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.data.KuttLink

@Composable
fun KuttLinkCard(link: KuttLink, onClick: () -> Unit) {
    // TODO: decide on card color to make it stick out just a slight bit more without being annoying
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .padding(8.dp)
        ) {
            Text(link.link, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(8.dp))
            IconText(icon = Icons.Default.Link, text = link.target)
            link.description?.let { IconText(icon = Icons.Default.Description, it) }
            // TODO: parse date string to something more human readable
            IconText(icon = Icons.Default.Schedule, text = link.created_at)
            IconText(icon = Icons.Default.Visibility, text = "${link.visit_count} views")
        }
    }
}

@Composable
fun IconText(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(4.dp))
        Text(text)
    }
}