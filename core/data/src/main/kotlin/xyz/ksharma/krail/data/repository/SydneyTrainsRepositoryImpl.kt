package xyz.ksharma.krail.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Response
import timber.log.Timber
import xyz.ksharma.krail.data.cacheZipResponse
import xyz.ksharma.krail.data.gtfs_static.parser.StopsParser.parseStops
import xyz.ksharma.krail.data.gtfs_static.parser.TripParser.parseTrips
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
    }
}
