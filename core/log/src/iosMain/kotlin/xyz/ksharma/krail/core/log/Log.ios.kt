package xyz.ksharma.krail.core.log

import platform.Foundation.NSLog
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual fun log(message: String) {
    if (Platform.isDebugBinary) NSLog("DEBUG: $message")
}

@OptIn(ExperimentalNativeApi::class)
actual fun logError(message: String, throwable: Throwable?) {
    if (!Platform.isDebugBinary) return
    if (throwable != null) {
        NSLog("ERROR: $message. Throwable: $throwable CAUSE ${throwable.cause}")
    } else {
        NSLog("ERROR: $message")
    }
}
