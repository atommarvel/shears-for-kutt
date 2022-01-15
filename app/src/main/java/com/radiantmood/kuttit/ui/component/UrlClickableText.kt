package com.radiantmood.kuttit.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.radiantmood.kuttit.util.tagUrls

@Composable
fun UrlClickableText(
    text: AnnotatedString,
    urlColor: Color = Color.Blue,
) {
    val uriHandler = LocalUriHandler.current
    val linkedText = buildAnnotatedString {
        append(text)
        tagUrls(urlColor)
    }
    ClickableMaterialText(
        linkedText,
        onClick = {
            text.getStringAnnotations("URL", it, it).firstOrNull()?.let { annotatedString ->
                uriHandler.openUri(annotatedString.item)
            }
        }
    )
}