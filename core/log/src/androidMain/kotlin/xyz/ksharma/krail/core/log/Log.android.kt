package xyz.ksharma.krail.core.log

import android.util.Log

actual fun log(message: String, throwable: Throwable?) {
    Log.d(getTag(), message)
}

actual fun logError(message: String, throwable: Throwable?) {
    Log.e(getTag(), message, throwable)
}

private fun getTag(): String {
    val maxTagLength = 23
    val stackTrace = Throwable().stackTrace

    val callerElement = stackTrace.firstOrNull { element ->
        element.className != "xyz.ksharma.krail.core.log.Log_androidKt" &&
                !element.className.startsWith("kotlin.") &&
                !element.className.startsWith("kotlinx.coroutines.") &&
                !element.methodName.contains("\$default")
    }

    if (callerElement != null) {
        val className = callerElement.className.substringAfterLast('.')
            .substringBefore('$') // Remove inner class markers
        val methodName =
            callerElement.methodName.substringBefore('$') // Remove Kotlin synthetic suffixes
        var refinedTag = "$className.$methodName"

        // Truncate or hash the tag if it exceeds the max length
        if (refinedTag.length > maxTagLength) {
            refinedTag = if (className.length > maxTagLength) {
                // Hash if even the class name is too long
                className.hashCode().toString()
            } else {
                // Truncate to maxTagLength
                refinedTag.take(maxTagLength)
            }
        }
/*

        println("Resolved Caller Element: $callerElement")
        println("Refined Tag: $refinedTag")

*/
        return refinedTag
    }

//    println("Caller not found, returning default tag.")
    return "Unknown"
}
