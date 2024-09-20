package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.design.system.theme.StartTheme
import xyz.ksharma.krail.trip_planner.network.api.model.StopType
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tripPlanningRepo: TripPlanningRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configures the system bars with a transparent background.
        enableEdgeToEdge()

        lifecycleScope.launch {
            var x = tripPlanningRepo.stopFinder(
                stopType = StopType.ANY,
                stopSearchQuery = "Seven Hills"
            )
            Timber.d("Seven Hills: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")
            x =
                tripPlanningRepo.stopFinder(stopType = StopType.ANY, stopSearchQuery = "Central")
            Timber.d("Central: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")
            x =
                tripPlanningRepo.stopFinder(stopType = StopType.ANY, stopSearchQuery = "Townhall")
            Timber.d("Townhall: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")
            x =
                tripPlanningRepo.stopFinder(stopType = StopType.ANY, stopSearchQuery = "Rockdale")
            Timber.d("Rockdale: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")


            var tripResponse = tripPlanningRepo.trip()
            tripResponse.onSuccess { trip ->
                Timber.d("Journeys: ${trip.journeys.size}")

                trip.journeys.map {
                    it.legs.forEach {
                        Timber.d(
                            "departureTimeEstimated: ${
                                it.origin.departureTimeEstimated?.utcToAEST()?.formatTo12HourTime()
                            }," +
                                    " Destination: ${
                                        it.destination.arrivalTimeEstimated?.utcToAEST()
                                            ?.formatTo12HourTime()
                                    }, " +
                                    "Duration: ${it.duration}, " +
                                    "transportation: ${it.transportation}",
                        )
                    }
                }

            }.onFailure {
                Timber.e("error: ${it.message}")
            }
        }

        setContent {
            StartTheme {
                KrailApp()
            }
        }
    }

    private fun String.utcToAEST(): String {
        // Parse the string as a ZonedDateTime in UTC
        val utcDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // Convert to AEST time zone (UTC+10)
        val aestZoneId = ZoneId.of("Australia/Sydney")
        val aestDateTime = utcDateTime.withZoneSameInstant(aestZoneId)
        return aestDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    fun String.formatTo12HourTime(): String {
        // Parse the string as ZonedDateTime
        val zonedDateTime = ZonedDateTime.parse(this)

        // Define the formatter for 12-hour time with AM/PM
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")

        // Format the ZonedDateTime to 12-hour format
        return zonedDateTime.format(timeFormatter)
    }
}
