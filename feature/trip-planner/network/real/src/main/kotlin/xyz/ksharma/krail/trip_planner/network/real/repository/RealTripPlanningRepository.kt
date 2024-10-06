package xyz.ksharma.krail.trip_planner.network.real.repository

import kotlinx.coroutines.CoroutineDispatcher
import xyz.ksharma.krail.coroutines.ext.suspendSafeResult
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.network.toSafeResult
import xyz.ksharma.krail.trip_planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip_planner.network.api.model.StopType
import xyz.ksharma.krail.trip_planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.network.api.service.TripPlanningService
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RealTripPlanningRepository @Inject constructor(
    private val tripPlanningService: TripPlanningService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TripPlanningRepository {

    override suspend fun stopFinder(
        stopType: StopType,
        stopSearchQuery: String,
    ): Result<StopFinderResponse> = suspendSafeResult(ioDispatcher) {
        tripPlanningService.stopFinder(
            typeSf = stopType.type,
            nameSf = stopSearchQuery
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
