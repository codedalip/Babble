package com.example.babble.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkBluePrimary,      // A cool teal for primary actions
    onPrimary = Color.Black,
    secondary = OrangeAccent,       // The vibrant orange for FABs and other accents
    onSecondary = Color.Black,
    background = DarkBackground,    // Deep grey background
    onBackground = DarkOnSurface,   // Off-white text on the background
    surface = DarkSurface,          // Slightly lighter grey for Cards
    onSurface = DarkOnSurface,      // Off-white text on Cards
    onSurfaceVariant = DarkSecondaryText, // For less important text on surfaces
    outline = DarkSecondaryText
)

private val LightColorScheme = lightColorScheme(
    primary = LightBluePrimary,     // Clean blue for primary actions
    onPrimary = Color.White,
    secondary = OrangeAccent,       // The vibrant orange for FABs
    onSecondary = Color.White,
    background = LightBackground,   // Light grey background
    onBackground = LightOnSurface,  // Dark grey text on the background
    surface = LightSurface,         // Pure white for Cards
    onSurface = LightOnSurface,     // Dark grey text on Cards
    onSurfaceVariant = LightSecondaryText, // For less important text
    outline = LightSecondaryText
)
@SuppressLint("NewApi")
@Composable
fun BabbleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode){
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = !darkTheme
            window.navigationBarDividerColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}