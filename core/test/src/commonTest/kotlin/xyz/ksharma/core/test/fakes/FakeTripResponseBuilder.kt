package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse.StopSequence

object FakeTripResponseBuilder {

    fun buildOriginStopSequence() = StopSequence(
        arrivalTimePlanned = "2024-09-24T19:00:00Z",
        arrivalTimeEstimated = "2024-09-24T19:10:00Z",
        departureTimePlanned = "2024-09-24T19:10:00Z",
        departureTimeEstimated = "2024-09-24T19:10:00Z",
        name = "Origin Stop",
        disassembledName = "Origin Name",
        id = "Origin_stop_id",
        type = StopType.STOP.type,
    )

    fun buildDestinationStopSequence() = StopSequence(
        arrivalTimePlanned = "2024-09-24T20:00:00Z",
        arrivalTimeEstimated = "2024-09-24T20:10:00Z",
        departureTimePlanned = "2024-09-24T20:10:00Z",
        departureTimeEstimated = "2024-09-24T20:10:00Z",
        name = "Destination Stop",
        disassembledName = "Destination Name",
        id = "Destination_stop_id",
        type = StopType.STOP.type,
    )

    fun buildTransportation() = TripResponse.Transportation(
        disassembledName = "Transportation Name",
        product = TripResponse.Product(
            productClass = 1,
            name = "Train",
        ),
        destination = TripResponse.OperatorClass(
            name = "Destination Operator",
            id = "Destination Operator Id",
        ),
        name = "Transportation Name",
        id = "Transportation Id",
        description = "Transportation Description",
    )
}
