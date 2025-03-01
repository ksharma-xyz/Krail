package xyz.ksharma.krail.trip.planner.ui.searchstop

import xyz.ksharma.krail.core.remote_config.flag.Flag
import xyz.ksharma.krail.core.remote_config.flag.FlagKeys
import xyz.ksharma.krail.core.remote_config.flag.toStopsIdList
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeSortOrder
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState

class StopPrioritizer(
    private val flag: Flag,
) {

    private val highlightStopIdList: List<String> by lazy {
        flag.getFlagValue(FlagKeys.HIGH_PRIORITY_STOP_IDS.key).toStopsIdList()
    }

    fun prioritiseStops(stopResults: List<SearchStopState.StopResult>): List<SearchStopState.StopResult> {
        val sortedTransportModes = TransportMode.sortedValues(TransportModeSortOrder.PRIORITY)
        val transportModePriorityMap = sortedTransportModes.mapIndexed { index, transportMode ->
            transportMode.productClass to index
        }.toMap()

        return stopResults.sortedWith(compareBy(
            { stopResult ->
                if (stopResult.stopId in highlightStopIdList) 0 else 1
            },
            { stopResult ->
                stopResult.transportModeType.minOfOrNull {
                    transportModePriorityMap[it.productClass] ?: Int.MAX_VALUE
                } ?: Int.MAX_VALUE
            },
            { it.stopName }
        ))
    }
}
