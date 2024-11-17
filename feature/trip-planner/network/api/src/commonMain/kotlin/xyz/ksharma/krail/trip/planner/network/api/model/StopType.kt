package xyz.ksharma.krail.trip.planner.network.api.model

enum class StopType(val type: String) {
    ANY(type = "any"),

    COORD(type = "coord"),

    POI(type = "poi"),

    STOP(type = "stop"),
}
