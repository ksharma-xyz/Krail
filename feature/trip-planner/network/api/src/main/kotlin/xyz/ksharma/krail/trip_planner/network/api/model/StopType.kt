package xyz.ksharma.krail.trip_planner.network.api.model

enum class StopType(val type: String) {
    ANY(type = "any"),

    COORD("coord"),

    POI("poi"),

    STOP("stop"),
}
