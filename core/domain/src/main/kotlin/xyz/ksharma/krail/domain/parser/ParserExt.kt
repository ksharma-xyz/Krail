package xyz.ksharma.krail.domain.parser

internal fun List<String>.trimQuotes(): List<String> = this.map { it.trim('\"') }
