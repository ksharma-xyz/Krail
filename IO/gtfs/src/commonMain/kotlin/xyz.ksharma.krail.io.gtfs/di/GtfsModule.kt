package xyz.ksharma.krail.io.gtfs.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.ksharma.krail.core.di.DispatchersComponent.Companion.IODispatcher
import xyz.ksharma.krail.io.gtfs.nswstops.ProtoParser
import xyz.ksharma.krail.io.gtfs.nswstops.StopsProtoParser

val gtfsModule = module {
    single<ProtoParser> {
        StopsProtoParser(ioDispatcher = get(named(IODispatcher)))
    }
}
