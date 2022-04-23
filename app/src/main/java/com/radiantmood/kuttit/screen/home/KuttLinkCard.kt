package com.radiantmood.kuttit.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.dev.preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun KuttLinkCard(link: KuttLink, isDeleting: Boolean, isDefaultExpanded: Boolean, onClick: () -> Unit) {
    // TODO#zbbng4: decide on card color to make it stick out just a slight bit more without being annoying
    val modifier = if (isDeleting) Modifier else Modifier.clickable { onClick() }
    val contentAlpha = if (isDeleting) 0.3f else 1f
    val (isExpanded, setIsExpanded) = remember(isDefaultExpanded) { mutableStateOf(isDefaultExpanded) }

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier.animateContentSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(contentAlpha)
            ) {
                LinkHeader(link, isExpanded, setIsExpanded)
                if (isExpanded) {
                    Spacer(Modifier.height(8.dp))
                    IconText(icon = Icons.Default.Link, text = link.target)
                    link.description?.let { IconText(icon = Icons.Default.Description, it) }
                    // TODO: parse date string to something more human readable
                    IconText(icon = Icons.Default.Schedule, text = link.created_at)
                    IconText(icon = Icons.Default.Visibility, text = "${link.visit_count} views")
                }
            }
            if (isDeleting) {
                DeletionOverlay(boxScope = this)
            }
        }
    }
}

@Composable
fun LinkHeader(link: KuttLink, isExpanded: Boolean, setIsExpanded: (Boolean) -> Unit) {
    Row {
        // TODO: ideally, this http:// removal should live outside the UI
        val linkDisplay = link.link.removePrefix("http://")
        Text(linkDisplay, style = MaterialTheme.typography.h6)
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable { setIsExpanded(!isExpanded) },
            contentAlignment = Alignment.CenterEnd
        ) {
            ExpandIcon(isExpanded = isExpanded)
        }
    }
}

@Composable
fun ExpandIcon(modifier: Modifier = Modifier, isExpanded: Boolean) {
    val expandIconRotation = animateFloatAsState(targetValue = if (isExpanded) 0f else 180f)
    Icon(
        modifier = modifier.graphicsLayer(rotationZ = expandIconRotation.value),
        imageVector = Icons.Default.ExpandLess,
        contentDescription = "expand"
    )
}

@Composable
fun DeletionOverlay(boxScope: BoxScope) {
    boxScope.run {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            val angle by flippingNumbers(initialValue = 25f)
            val animatedAngle by animateFloatAsState(targetValue = angle, tween())
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Deleting",
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .align(Alignment.Center)
                    .rotate(animatedAngle),
                tint = Color.Red
            )
        }
    }
}

@Composable
fun flippingNumbers(initialValue: Float): State<Float> {
    return produceState(initialValue = initialValue) {
        while (isActive) {
            delay(301) // TODO#1ney5nn: magic number for animation durations
            value *= -1
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

@Preview
@Composable
private fun DefaultPreview() {
    KuttLinkCard(link = KuttLink.preview(), isDeleting = false, isDefaultExpanded = true, onClick = {})
}