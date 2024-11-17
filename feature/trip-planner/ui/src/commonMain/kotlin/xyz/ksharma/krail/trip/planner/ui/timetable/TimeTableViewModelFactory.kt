package xyz.ksharma.krail.trip.planner.ui.timetable

import kotlinx.coroutines.CoroutineDispatcher
import me.tatarka.inject.annotations.Inject
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher

@Inject
class TimeTableViewModelFactory(
    private val tripRepository: TripPlanningRepository,
    private val sandookFactory: SandookFactory,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val rateLimiter: RateLimiter
) {
    fun create(): TimeTableViewModel {
        return TimeTableViewModel(tripRepository, sandookFactory, ioDispatcher, rateLimiter)
    }
}
