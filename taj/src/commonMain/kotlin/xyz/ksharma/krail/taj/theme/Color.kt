package xyz.ksharma.krail.taj.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1F1B16)
val md_theme_light_scrim = Color(0xFF000000)
val md_theme_light_alert = Color(0xFFFFBA27)

// Dark Color tokens
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_surface = Color(0xFF1F1B16)
val md_theme_dark_onSurface = Color(0xFFEAE1D9)
val md_theme_dark_scrim = Color(0xFF000000)
val md_theme_dark_alert = Color(0xFFF4B400)

val bus_theme = Color(0xFF00B5EF)
val train_theme = Color(0xFFF6891F)
val metro_theme = Color(0xFF009B77)
val ferry_theme = Color(0xFF5AB031)
val coach_theme = Color(0xFF742282)
val light_rail_theme = Color(0xFFEE343F)

val seed = Color(0xFFFFBA27)

@Immutable
data class KrailColors(
    val label: Color,
    val busTheme: Color,
    val onBusTheme: Color,
    val trainTheme: Color,
    val onTrainTheme: Color,
    val ferryTheme: Color,
    val onFerryTheme: Color,
    val coachTheme: Color,
    val onCoachTheme: Color,
    val metroTheme: Color,
    val onMetroTheme: Color,
    val lightRailTheme: Color,
    val onLightRailTheme: Color,
    val error: Color,
    val errorContainer: Color,
    val onError: Color,
    val onErrorContainer: Color,
    val surface: Color,
    val onSurface: Color,
    val scrim: Color,
    val alert: Color,
)

internal val KrailLightColors = KrailColors(
    label = md_theme_light_onSurface,
    busTheme = bus_theme,
    onBusTheme = md_theme_light_onSurface,
    trainTheme = train_theme,
    onTrainTheme = md_theme_light_onSurface,
    ferryTheme = ferry_theme,
    onFerryTheme = md_theme_light_onSurface,
    coachTheme = coach_theme,
    onCoachTheme = md_theme_light_surface,
    metroTheme = metro_theme,
    onMetroTheme = md_theme_light_onSurface,
    lightRailTheme = light_rail_theme,
    onLightRailTheme = md_theme_light_onSurface,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    scrim = md_theme_light_scrim,
    alert = md_theme_light_alert,
)

internal val KrailDarkColors = KrailColors(
    label = md_theme_dark_onSurface,
    busTheme = bus_theme,
    onBusTheme = md_theme_dark_surface,
    trainTheme = train_theme,
    onTrainTheme = md_theme_dark_onSurface,
    ferryTheme = ferry_theme,
    onFerryTheme = md_theme_dark_surface,
    coachTheme = coach_theme,
    onCoachTheme = md_theme_dark_onSurface,
    metroTheme = metro_theme,
    onMetroTheme = md_theme_dark_onSurface,
    lightRailTheme = light_rail_theme,
    onLightRailTheme = md_theme_dark_surface,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    scrim = md_theme_dark_scrim,
    alert = md_theme_dark_alert,
)

internal val LocalKrailColors = staticCompositionLocalOf {
    KrailColors(
        label = Color.Unspecified,
        busTheme = Color.Unspecified,
        onBusTheme = Color.Unspecified,
        trainTheme = Color.Unspecified,
        onTrainTheme = Color.Unspecified,
        ferryTheme = Color.Unspecified,
        onFerryTheme = Color.Unspecified,
        coachTheme = Color.Unspecified,
        onCoachTheme = Color.Unspecified,
        metroTheme = Color.Unspecified,
        onMetroTheme = Color.Unspecified,
        lightRailTheme = Color.Unspecified,
        onLightRailTheme = Color.Unspecified,
        error = Color.Unspecified,
        errorContainer = Color.Unspecified,
        onError = Color.Unspecified,
        onErrorContainer = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        scrim = Color.Unspecified,
        alert = Color.Unspecified,
    )
}
