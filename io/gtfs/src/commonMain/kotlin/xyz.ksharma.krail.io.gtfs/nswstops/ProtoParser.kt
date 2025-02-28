package xyz.ksharma.krail.io.gtfs.nswstops

import app.krail.kgtfs.proto.NswStopList

interface ProtoParser {
    /**
     * Reads and decodes the NSW stops from a protobuf file, then inserts the stops into the database.
     * @return The decoded `NswStopList` containing the NSW stops.
     */
    suspend fun parseAndInsertStops(): NswStopList?
}
