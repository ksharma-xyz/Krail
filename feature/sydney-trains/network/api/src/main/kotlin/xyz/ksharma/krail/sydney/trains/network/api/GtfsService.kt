package xyz.ksharma.krail.sydney.trains.network.api

import okhttp3.Response

interface SydneyTrainsService {

    suspend fun getSydneyTrainsStaticData(): Result<Response>
}
