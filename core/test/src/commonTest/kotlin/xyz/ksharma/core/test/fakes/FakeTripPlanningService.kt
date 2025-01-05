package xyz.ksharma.core.test.fakes

import xyz.ksharma.core.test.fakes.FakeTripResponseBuilder.buildDestinationStopSequence
import xyz.ksharma.core.test.fakes.FakeTripResponseBuilder.buildOriginStopSequence
import xyz.ksharma.core.test.fakes.FakeTripResponseBuilder.buildTransportation
import xyz.ksharma.krail.trip.planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse.StopSequence
import xyz.ksharma.krail.trip.planner.network.api.service.DepArr
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService

class FakeTripPlanningService : TripPlanningService {

    var isSuccess: Boolean = true

    override suspend fun trip(
        originStopId: String,
        destinationStopId: String,
        depArr: DepArr,
        date: String?,
        time: String?,
    ): TripResponse {
        return if (isSuccess)

        // Return a fake TripResponse
            TripResponse(
                journeys = listOf(
                    TripResponse.Journey(
                        legs = listOf(
                            TripResponse.Leg(
                                origin = buildOriginStopSequence(),
                                destination = buildDestinationStopSequence(),
                                stopSequence = listOf(
                                    buildOriginStopSequence(),
                                ),
                                transportation = buildTransportation(),
                                duration = 100,
                            ),
                        ),
                    ),
                )
            )
        else throw IllegalStateException("Failed to fetch trip")
    }

    override suspend fun stopFinder(
        stopSearchQuery: String,
        stopType: StopType,
    ): StopFinderResponse {
        // Return a fake StopFinderResponse
        return StopFinderResponse(
        )
    }
}
