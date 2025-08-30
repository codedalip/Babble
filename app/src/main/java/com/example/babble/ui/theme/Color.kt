// In package com.example.babble.ui.theme
package com.example.babble.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val OrangeAccent = Color(0xFFFF7A5C)
val OrangeAccentDark = Color(0xFFD9684C)

//light theme color

val LightBluePrimary = Color(0xFF007BFF)
val LightBackground = Color(0xFFBEE6FF)
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF212529)
val LightSecondaryText = Color(0xFF6C757D)


//Dark theme color

val DarkBluePrimary = Color(0xFF4DB6AC)
val DarkBackground = Color(0xFF1E1E1E)
val DarkSurface = Color(0xFF1E1E1E)
val DarkOnSurface = Color(0xFFE0E0E0)
val DarkSecondaryText = Color(0xFFAAAAAA)

val myGradient = Brush.linearGradient(
    colors = listOf(Color.Blue, Color.Magenta),
    start = Offset.Zero,
    end = Offset.Infinite
)