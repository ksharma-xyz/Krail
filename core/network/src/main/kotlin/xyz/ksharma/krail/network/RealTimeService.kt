package xyz.ksharma.krail.network

import retrofit2.Response
import retrofit2.http.GET

interface RealTimeService {

    @GET("v1/gtfs/schedule/sydneytrains")
    fun getRealtimeSydneyTrainsSchedule(): Any
}
