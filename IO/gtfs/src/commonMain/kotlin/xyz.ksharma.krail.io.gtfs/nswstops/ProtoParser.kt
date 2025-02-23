package xyz.ksharma.krail.io.gtfs.nswstops

import app.krail.kgtfs.proto.NswStopList

interface ProtoParser {
    /**
     * Reads the stops from the proto file packaged in the resources
     */
    suspend fun readStops(): NswStopList?
}
