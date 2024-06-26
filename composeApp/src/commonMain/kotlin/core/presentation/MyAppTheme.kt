package core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ui.theme.AppTypography
import ui.theme.LocalSpacing
import ui.theme.Spacing
import ui.theme.darkScheme
import ui.theme.lightScheme

@Composable
fun MyAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(value = LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = if (darkTheme) darkScheme else lightScheme,
            typography = AppTypography,
            content = content
        )
    }
}

@Composable
expect fun SystemAppearance(isDark: Boolean)