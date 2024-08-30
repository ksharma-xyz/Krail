package xyz.ksharma.krail.data.gtfs_static.parser

internal fun List<String>.trimQuotes(): List<String> = this.map { it.trim('\"') }
