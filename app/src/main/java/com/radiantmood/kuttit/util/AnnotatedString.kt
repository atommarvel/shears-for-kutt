package com.radiantmood.kuttit.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

fun AnnotatedString.Builder.tagUrls(urlColor: Color = Color.Blue) {
    val text = this.toAnnotatedString().text
    val urlRegex =
        Regex("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})")
    urlRegex.findAll(text).forEach { match ->
        addStyle(SpanStyle(textDecoration = TextDecoration.Underline, color = urlColor),
            match.range.first,
            match.range.last + 1)
        addStringAnnotation("URL", match.value, match.range.first, match.range.last + 1)
    }
}