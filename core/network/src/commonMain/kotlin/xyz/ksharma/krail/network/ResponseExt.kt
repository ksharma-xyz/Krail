package xyz.ksharma.krail.network

import retrofit2.Response

/**
 * Processes a Retrofit response and returns a [Result] object.
 *
 * If the response is successful, the body is extracted and wrapped in a [Result.success].
 * If the response is unsuccessful, an [Exception] is created with the error code and wrapped in a [Result.failure].
 *
 * @return A [Result] object containing the response body or an error.
 */
fun <T> Response<T>.toSafeResult(): Result<T> {
    return if (this.isSuccessful) {
        this.body()?.let { body ->
            Result.success(body)
        } ?: Result.failure(Exception("Response body is null"))
    } else {
        Result.failure(Exception("API call failed with error: ${this.code()}"))
    }
}
