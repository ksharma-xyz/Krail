package xyz.ksharma.krail.trip.planner.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

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
