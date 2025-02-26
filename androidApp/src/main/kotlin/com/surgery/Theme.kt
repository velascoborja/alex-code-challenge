package com.surgery

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgery.composable.DarkGreen10
import com.surgery.composable.DarkGreen20
import com.surgery.composable.DarkGreen30
import com.surgery.composable.DarkGreen40
import com.surgery.composable.DarkGreen80
import com.surgery.composable.DarkGreen90
import com.surgery.composable.DarkGreenGray10
import com.surgery.composable.DarkGreenGray20
import com.surgery.composable.DarkGreenGray90
import com.surgery.composable.DarkGreenGray95
import com.surgery.composable.DarkGreenGray99
import com.surgery.composable.DarkPurpleGray90
import com.surgery.composable.Green10
import com.surgery.composable.Green20
import com.surgery.composable.Green30
import com.surgery.composable.Green40
import com.surgery.composable.Green80
import com.surgery.composable.Green90
import com.surgery.composable.GreenGray30
import com.surgery.composable.GreenGray50
import com.surgery.composable.GreenGray60
import com.surgery.composable.GreenGray80
import com.surgery.composable.GreenGray90
import com.surgery.composable.Red10
import com.surgery.composable.Red20
import com.surgery.composable.Red30
import com.surgery.composable.Red40
import com.surgery.composable.Red80
import com.surgery.composable.Red90
import com.surgery.composable.Teal10
import com.surgery.composable.Teal20
import com.surgery.composable.Teal30
import com.surgery.composable.Teal40
import com.surgery.composable.Teal80
import com.surgery.composable.Teal90

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkAndroidColorScheme else LightAndroidColorScheme
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(16.dp),
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content,
    )
}

val LightAndroidColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = DarkPurpleGray90,
    onPrimaryContainer = Green10,
    secondary = DarkGreen40,
    onSecondary = Color.White,
    secondaryContainer = DarkGreen90,
    onSecondaryContainer = DarkGreen10,
    tertiary = Teal40,
    onTertiary = Color.White,
    tertiaryContainer = Teal90,
    onTertiaryContainer = Teal10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = DarkGreenGray99,
    onBackground = DarkGreenGray10,
    surface = DarkGreenGray99,
    onSurface = DarkGreenGray10,
    surfaceVariant = GreenGray90,
    onSurfaceVariant = GreenGray30,
    inverseSurface = DarkGreenGray20,
    inverseOnSurface = DarkGreenGray95,
    outline = GreenGray50,
)

val DarkAndroidColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,
    secondary = DarkGreen80,
    onSecondary = DarkGreen20,
    secondaryContainer = DarkGreen30,
    onSecondaryContainer = DarkGreen90,
    tertiary = Teal80,
    onTertiary = Teal20,
    tertiaryContainer = Teal30,
    onTertiaryContainer = Teal90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkGreenGray10,
    onBackground = DarkGreenGray90,
    surface = DarkGreenGray10,
    onSurface = DarkGreenGray90,
    surfaceVariant = GreenGray30,
    onSurfaceVariant = GreenGray80,
    inverseSurface = DarkGreenGray90,
    inverseOnSurface = DarkGreenGray10,
    outline = GreenGray60,
)