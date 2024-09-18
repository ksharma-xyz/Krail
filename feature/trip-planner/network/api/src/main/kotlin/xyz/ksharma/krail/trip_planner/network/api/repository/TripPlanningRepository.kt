package xyz.ksharma.krail.trip_planner.network.api.repository

import xyz.ksharma.krail.trip_planner.network.api.model.StopType

interface TripPlanningRepository {

    suspend fun stopFinder(stopType: StopType, stopSearchQuery: String)

    fun trip()
}
