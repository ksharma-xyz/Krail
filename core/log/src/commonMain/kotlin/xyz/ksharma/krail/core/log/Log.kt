package xyz.ksharma.krail.core.log

expect fun log(message: String)

expect fun logError(message: String, throwable: Throwable? = null)
