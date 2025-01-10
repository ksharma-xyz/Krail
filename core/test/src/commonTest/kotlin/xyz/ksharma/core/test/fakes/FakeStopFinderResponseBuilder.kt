package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.trip.planner.network.api.model.StopFinderResponse

object FakeStopFinderResponseBuilder {

    fun buildStopFinderResponse(): StopFinderResponse {
        return StopFinderResponse(
            locations = listOf(
                StopFinderResponse.Location(
                    id = "1",
                    name = "Stop 1",
                    type = "stop",
                    disassembledName = "Stop 1, Suburb 1",
                    properties = StopFinderResponse.Properties("1"),
                    productClasses = listOf(1, 5, 9),
                ),
                StopFinderResponse.Location(
                    id = "2",
                    name = "Stop 2",
                    type = "stop",
                    disassembledName = "Stop 2, Suburb 2",
                    properties = StopFinderResponse.Properties("2"),
                    productClasses = listOf(1, 2),
                ),
            ),
        )
    }
}
