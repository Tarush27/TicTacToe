package com.example.tictactoe.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class AppTheme {
    DEFAULT, RED, YELLOW
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80, surface = Color.Black, onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40, surface = Color.White, onSurface = Color.Black
)

private val RedColorScheme = lightColorScheme(
    primary = Red40, surface = Red80, onSurface = Color.Black
)

private val YellowColorScheme = lightColorScheme(
    primary = Yellow40, surface = Yellow80, onSurface = Color.Black
)

@Composable
fun TicTacToeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    appTheme: AppTheme = AppTheme.DEFAULT,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val colorScheme = when (appTheme) {
        AppTheme.RED -> RedColorScheme
        AppTheme.YELLOW -> YellowColorScheme
        AppTheme.DEFAULT -> {
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                        context
                    )
                }

                darkTheme -> DarkColorScheme
                else -> LightColorScheme
            }
        }
    }

    val useDarkIcons = colorScheme.background.luminance() > 0.5f

    if (!view.isInEditMode) {

            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = useDarkIcons

    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}
