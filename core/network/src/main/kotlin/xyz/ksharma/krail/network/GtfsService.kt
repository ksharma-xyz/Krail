package xyz.ksharma.krail.network

import okhttp3.Response

interface GtfsService {

    suspend fun getSydneyTrainSchedule(): Response
}
