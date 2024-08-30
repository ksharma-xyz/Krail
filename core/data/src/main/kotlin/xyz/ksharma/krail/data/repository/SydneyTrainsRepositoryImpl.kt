package xyz.ksharma.krail.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Response
import timber.log.Timber
import xyz.ksharma.krail.data.cacheZipResponse
import xyz.ksharma.krail.data.gtfs_static.parser.AgencyParser.parseAgency
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.model.sydneytrains.GTFSFeedFileNames
import xyz.ksharma.krail.network.GtfsService
import xyz.ksharma.krail.network.files.toPath
import javax.inject.Inject

class SydneyTrainsRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val gtfsService: GtfsService,
) : SydneyTrainsRepository {

    override suspend fun fetchStaticSydneyTrainsScheduleAndCache(): Unit = withContext(ioDispatcher) {
        Timber.d( "getSydneyTrains: ")
        val response: Response = gtfsService.getSydneyTrainSchedule()
        response.cacheZipResponse(dispatcher = ioDispatcher, context = context)

        // TODO - provide boolean response if files were cached successfully.
        //val stopsList = context.toPath(GTFSFeedFileNames.STOPS.fileName).parseStops()
        //Timber.d("stopsList: $stopsList")
        //val tripsList = context.toPath(GTFSFeedFileNames.TRIPS.fileName).parseTrips()
        //val stopTimesList = context.toPath(GTFSFeedFileNames.STOP_TIMES.fileName).parseStopTimes()
        //Timber.d("stopTimesList: ${stopTimesList.size}") Huge data do not log.
        //val routesList = context.toPath(GTFSFeedFileNames.ROUTES.fileName).parseRoutes()
//        Timber.d("routesList: $routesList")
        //val occupancyList = context.toPath(GTFSFeedFileNames.OCCUPANCIES.fileName).parseOccupancy()
        //Timber.d("occupancyList: ${occupancyList.size}")
        //val calendarList = context.toPath(GTFSFeedFileNames.CALENDAR.fileName).parseCalendar()
        //Timber.d("calendarList: ${calendarList}")
        val agencyList = context.toPath(GTFSFeedFileNames.AGENCY.fileName).parseAgency()
        Timber.d("agencyList: $agencyList")
    }
}
