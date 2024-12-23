package xyz.ksharma.krail.trip.planner.ui.timetable

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Test
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService

@OptIn(ExperimentalCoroutinesApi::class)
class TimeTableViewModelTest {

    private lateinit var viewModel: TimeTableViewModel
    private val tripPlanningService: TripPlanningService = mockk()
    private val rateLimiter: RateLimiter = mockk()
    private val sandook: Sandook = mockk()

    @Before
    fun setUp() {
        viewModel = TimeTableViewModel(tripPlanningService, rateLimiter, sandook)
    }

    @Test
    fun `updateTripsCache should update trips cache correctly`() = runTest {
        val response = mockk<TripResponse>()
        val journeyCardInfo = TimeTableState.JourneyCardInfo(
            timeText = "12:00",
            platformText = "Stand A",
            platformNumber = "A",
            originTime = "12:00",
            destinationTime = "12:30",
            travelTime = "30 mins",
            transportModeLines = emptyList(),
            legs = emptyList(),
            totalUniqueServiceAlerts = 3,
            originUtcDateTime = "2024-11-01T12:00:00Z",
            destinationUtcDateTime = "2024-11-01T12:30:00Z"
        )

        coEvery { response.buildJourneyList() } returns listOf(journeyCardInfo)

        withContext(Dispatchers.IO) {
            viewModel.updateTripsCache(response)
        }

        assertEquals(1, viewModel.trips.size)
        assertTrue(viewModel.trips.containsKey("1"))
    }
}
