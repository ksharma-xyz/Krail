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
            var x =
                tripPlanningRepo.stopFinder(stopType = StopType.ANY, stopSearchQuery = "Central")
            Timber.d("ANY: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")

            var tripResponse = tripPlanningRepo.trip()
            tripResponse.onSuccess { trip ->
                Timber.d("Journeys: ${trip.journeys?.size}")
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
}
