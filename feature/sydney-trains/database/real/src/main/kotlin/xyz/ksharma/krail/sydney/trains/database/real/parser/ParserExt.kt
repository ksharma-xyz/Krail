package xyz.ksharma.krail.sydney.trains.database.real.parser

internal fun List<String>.trimQuotes(): List<String> = this.map { it.trim('\"') }
