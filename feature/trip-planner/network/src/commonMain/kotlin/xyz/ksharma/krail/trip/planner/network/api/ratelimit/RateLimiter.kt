package xyz.ksharma.krail.trip.planner.network.api.ratelimit

import kotlinx.coroutines.flow.Flow

/**
 * Implements rate limiting for API calls using Kotlin Flows.
 * This ensures that API calls are made at a controlled rate to avoid hitting rate limits.
 */
interface RateLimiter {

    /**
     * Returns a Flow that emits the result of the provided API call, ensuring that the calls are rate-limited.
     *
     * @param block A suspend function representing the API call to be rate-limited.
     * @return A Flow that emits the result of the API call.
     */
    fun <T> rateLimitFlow(block: suspend () -> T): Flow<T>

    /**
     * Triggers the API call by emitting a new item to the triggerFlow.
     * This method should be called whenever an API call needs to be made.
     */
    fun triggerEvent(): Boolean
}
