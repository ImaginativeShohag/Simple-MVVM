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
    primaryVariant = Color(0xFF28A05E),
    secondary = Color(0xFF2BAE66),
    secondaryVariant = Color(0xFF28A05E),
    surface = Color(0xFFF1F4F3),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF2F4858),
    onSurface = Color(0xFF2F4858),

    /* Other default colors to override
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
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