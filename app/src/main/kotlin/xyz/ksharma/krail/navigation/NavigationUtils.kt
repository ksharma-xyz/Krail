package xyz.ksharma.krail.navigation

import java.net.URLDecoder

internal fun String.decode(): String {
    return URLDecoder.decode(this, URL_CHARACTER_ENCODING)
}

internal val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()
