package xyz.ksharma.krail.trip.planner.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

// TODO - Can be removed once updated to AndroidX Activity starting from
//  androidx.activity:activity-compose:1.10.0-alpha03
fun Context.getActivityOrNull(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Composable
internal fun DefaultSystemBarColors() {
    val context = LocalContext.current
    val darkIcons = isSystemInDarkTheme()
    DisposableEffect(darkIcons) {
        context.getActivityOrNull()?.let { activity ->
            (activity as ComponentActivity).enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                ) { darkIcons },
            )
        }
        onDispose {}
    }
}
