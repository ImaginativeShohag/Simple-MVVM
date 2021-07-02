package org.imaginativeworld.simplemvvm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.softzino.rentacardirectory.theme.Shapes

private val DarkColorPalette = darkColors(

)

private val LightColorPalette = lightColors(
    primary = Color(0xFF2BAE66),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF28A05E),
    secondary = Color(0xFF2BAE66),
    onSecondary = Color.White,
    secondaryVariant = Color(0xFF28A05E),
    surface = Color(0xFFF1F4F3),
    onSurface = Color(0xFF2F4858),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF2F4858),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}