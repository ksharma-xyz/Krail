package xyz.ksharma.krail.domain

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * [DemoUseCase] will fetch the data from [SydneyTrainsRepository] and map the
 * data model objects into domain model.
 *
 * It will also wrap the data into a [Result], so as to provide Error, Success and Loading
 * states for UI.
 */
interface DemoUseCase {
    suspend operator fun invoke()
}

class DemoUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DemoUseCase {
    override suspend operator fun invoke() {
        // val stopsList = context.toPath(GTFSFeedFileNames.STOPS.fileName).parseStops()
        // Timber.d("stopsList: $stopsList")
        // val tripsList = context.toPath(GTFSFeedFileNames.TRIPS.fileName).parseTrips()
        // val stopTimesList = context.toPath(GTFSFeedFileNames.STOP_TIMES.fileName).parseStopTimes()
        // Timber.d("stopTimesList: ${stopTimesList.size}") Huge data do not log.
        // val routesList = context.toPath(GTFSFeedFileNames.ROUTES.fileName).parseRoutes()
//        Timber.d("routesList: $routesList")
        // val occupancyList = context.toPath(GTFSFeedFileNames.OCCUPANCIES.fileName).parseOccupancy()
        // Timber.d("occupancyList: ${occupancyList.size}")
        // val calendarList = context.toPath(GTFSFeedFileNames.CALENDAR.fileName).parseCalendar()
        // Timber.d("calendarList: ${calendarList}")
       /* val agencyList = context.toPath(GTFSFeedFileNames.AGENCY.fileName).parseAgency()
        Timber.d("agencyList: $agencyList")*/
    }
}
