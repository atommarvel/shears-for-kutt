package com.radiantmood.kuttit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

val primaryGradient
    @Composable
    get() = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.primaryVariant
        )
    )

private val DarkColorPalette = darkColors(
    primary = CornflowerBlue,
    primaryVariant = MayaBlue,
)

private val LightColorPalette = lightColors(
    primary = CornflowerBlue,
    primaryVariant = MayaBlue,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun KuttItTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        // I'm not really feeling the font anymore. Commenting it out for now.
        // typography = Typography,
        shapes = Shapes,
        content = content
    )
}