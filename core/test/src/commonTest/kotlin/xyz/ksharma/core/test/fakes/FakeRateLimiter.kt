package xyz.ksharma.core.test.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.RateLimiter

class FakeRateLimiter : RateLimiter {

    private var eventTriggered = false

    override fun <T> rateLimitFlow(block: suspend () -> T): Flow<T> {
        return flow { emit(block()) }
    }

    override fun triggerEvent(): Boolean {
        eventTriggered = true
        return eventTriggered
    }
}
