package xyz.ksharma.krail.trip_planner.network.api.repository

import xyz.ksharma.krail.trip_planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip_planner.network.api.model.StopType
import xyz.ksharma.krail.trip_planner.network.api.model.TripResponse

interface TripPlanningRepository {

    suspend fun stopFinder(stopType: StopType, stopSearchQuery: String): Result<StopFinderResponse>

    suspend fun trip(): Result<TripResponse>
}
