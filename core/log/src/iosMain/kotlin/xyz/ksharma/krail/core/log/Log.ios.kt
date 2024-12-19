package xyz.ksharma.krail.core.log

import platform.Foundation.NSLog

actual fun log(message: String, throwable: Throwable?) = NSLog("DEBUG: $message")

actual fun logError(message: String, throwable: Throwable?) = if (throwable != null) {
    NSLog("ERROR: $message. Throwable: $throwable CAUSE ${throwable.cause}")
} else {
    NSLog("ERROR: $message")
}
