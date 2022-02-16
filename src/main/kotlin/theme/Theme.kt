package theme

import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp


object Colors {
    val backgroundDark: Color = Color(0xFF2B2B2B)
    val backgroundMedium: Color = Color(0xFF3C3F41)
    val backgroundLight: Color = Color(0xFF4E5254)

    val material: androidx.compose.material.Colors = darkColors(
        background = backgroundDark,
        surface = backgroundMedium,
        primary = Color.White
    )
}

val CirceFontFamily = FontFamily(
    Font("fonts/circe-light.ttf", FontWeight.Light,  FontStyle.Normal),
    Font("fonts/circe.ttf", FontWeight.Normal,  FontStyle.Normal),
    Font("fonts/circe-bold.ttf", FontWeight.Bold,  FontStyle.Normal),
)

val CirceFontBoldFamily = FontFamily(
    Font("fonts/circe-bold.ttf", FontWeight.Bold,  FontStyle.Normal)
)

val typography = Typography(
    defaultFontFamily = CirceFontFamily,
    h1 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
        color = TelegramColors.mainText
    ),
    h2 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
        color = TelegramColors.mainText
    ),
    h3 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        letterSpacing = 0.sp,
        color = TelegramColors.mainText
    ),
    h4 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
        color = TelegramColors.mainText
    ),
    h5 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        color = TelegramColors.mainText
    ),
    h6 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        color = TelegramColors.mainText
    ),
    subtitle1 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        color = TelegramColors.mainText
    ),
    body1 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        color = TelegramColors.mainText
    ),
    body2 = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        color = TelegramColors.mainText
    ),
    button = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        color = TelegramColors.mainText
    ),
    caption = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        color = TelegramColors.mainText
    ),
    overline = TextStyle(
        fontFamily = CirceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.sp,
        color = TelegramColors.mainText
    )
)