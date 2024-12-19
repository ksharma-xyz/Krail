package xyz.ksharma.krail.core.log

expect fun log(message: String, throwable: Throwable? = null)

expect fun logError(message: String, throwable: Throwable? = null)
