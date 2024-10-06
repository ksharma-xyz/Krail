package xyz.ksharma.krail.trip_planner.ui.state.searchstop.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import xyz.ksharma.krail.trip_planner.domain.model.TransportModeType

/**
 * Represents a Stop item in the search results when searching for stops.
 */
data class StopItem(
    val stopName: String,
    val transportModes: ImmutableSet<TransportModeType> = persistentSetOf(),
    val stopId: String,
)
