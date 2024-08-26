package xyz.ksharma.krail.model.sydneytrains

enum class GTFSFeedFileNames(val fileName: String) {
    AGENCY(fileName = "agency.txt"),
    ROUTES(fileName = "routes.txt"),
    SHAPES(fileName = "shapes.txt"),
    STOPS(fileName = "stops.txt"),
    VEHICLE_CATEGORIES(fileName = "vehicle_categories.txt"),
    VEHICLE_BOARDINGS(fileName = "vehicle_boardings.txt"),
    VEHICLE_COUPLINGS(fileName = "vehicle_couplings.txt"),
    CALENDAR(fileName = "calendar.txt"),
    TRIPS(fileName = "trips.txt"),
    STOP_TIMES(fileName = "stop_times.txt"),
    OCCUPANCIES(fileName = "occupancies.txt"),
}
