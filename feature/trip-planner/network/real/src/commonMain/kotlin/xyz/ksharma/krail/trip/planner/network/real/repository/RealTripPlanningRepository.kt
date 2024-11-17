package xyz.ksharma.krail.trip.planner.network.real.repository

import kotlinx.coroutines.CoroutineDispatcher
import me.tatarka.inject.annotations.Inject
import xyz.ksharma.krail.coroutines.ext.suspendSafeResult
import xyz.ksharma.krail.trip.planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.repository.TripPlanningRepository

class RealTripPlanningRepository @Inject constructor(
    val ioDispatcher: CoroutineDispatcher,
) : TripPlanningRepository {

    override suspend fun stopFinder(
        stopType: StopType,
        stopSearchQuery: String,
    ): Result<StopFinderResponse> = suspendSafeResult(ioDispatcher) {
        tripPlanningService.stopFinder(
            typeSf = stopType.type,
            nameSf = stopSearchQuery,
        ).toSafeResult()
    }

    override suspend fun trip(
        originStopId: String,
        destinationStopId: String,
        journeyTime: String?,
    ): Result<TripResponse> = suspendSafeResult(ioDispatcher) {
        tripPlanningService.trip(
            depArrMacro = "dep",
            nameOrigin = originStopId,
            nameDestination = destinationStopId,
            itdTime = journeyTime,
        ).toSafeResult()
    }
}

/** Stop Ids
 * Rockdale - 221620
 * Central - 200060
 * TownHall - 200070
 * SevenHills - 214710
 * NewTown - 204210
 */
