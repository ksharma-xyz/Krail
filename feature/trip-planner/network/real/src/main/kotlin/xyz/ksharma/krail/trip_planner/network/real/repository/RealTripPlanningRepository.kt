package xyz.ksharma.krail.trip_planner.network.real.repository

import kotlinx.coroutines.CoroutineDispatcher
import xyz.ksharma.krail.coroutines.ext.suspendSafeResult
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.network.toSafeResult
import xyz.ksharma.krail.trip_planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip_planner.network.api.model.StopType
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.network.api.service.TripPlanningService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealTripPlanningRepository @Inject constructor(
    private val tripPlanningService: TripPlanningService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TripPlanningRepository {

    override suspend fun stopFinder(
        stopType: StopType,
        stopSearchQuery: String,
    ): Result<StopFinderResponse> = suspendSafeResult(ioDispatcher) {
        tripPlanningService.stopFinder(typeSf = stopType.type, nameSf = stopSearchQuery)
            .toSafeResult()
    }

    override fun trip() {
        TODO("Not yet implemented")
    }
}
