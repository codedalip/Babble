package com.example.babble.ui.theme

import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babble.R

// Set of Material typography styles to start with

val Anton = FontFamily(
    Font(R.font.anton_regular)
)
val Cabin = FontFamily(
    Font(R.font.cabin_regular),
    Font(R.font.cabin_semibold),
    Font(R.font.cabin_bold),
)
val Poet = FontFamily(
    Font(R.font.poetsenone_regular)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Anton,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Poet,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )
)
/* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
