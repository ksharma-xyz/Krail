package xyz.ksharma.krail.trip_planner.network.real.repository

import xyz.ksharma.krail.trip_planner.network.api.model.StopType
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.network.api.service.TripPlanningService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealTripPlanningRepository @Inject constructor(
    private val tripPlanningService: TripPlanningService,
) : TripPlanningRepository {

    override suspend fun stopFinder(stopType: StopType, stopSearchQuery: String) {
        tripPlanningService.stopFinder(typeSf = stopType.type, nameSf = stopSearchQuery)
    }

    override fun trip() {
        TODO("Not yet implemented")
    }
}
