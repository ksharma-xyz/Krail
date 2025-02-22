package xyz.ksharma.krail.io.gtfs.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.io.gtfs.nswstops.ProtoParser
import xyz.ksharma.krail.io.gtfs.nswstops.StopsProtoParser

val gtfsModule = module {
    singleOf(::StopsProtoParser) { bind<ProtoParser>() }
    includes(resourceLoaderModule)
}

expect val resourceLoaderModule: Module
