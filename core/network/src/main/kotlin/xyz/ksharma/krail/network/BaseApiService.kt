package xyz.ksharma.krail.network

import okhttp3.OkHttpClient
import okhttp3.Request

interface BaseApiService {

    val okHttpClient: OkHttpClient

    suspend fun <T> get(url: String, responseClass: Class<T>): T

    suspend fun <T> post(url: String, body: Any, responseClass: Class<T>): T

    suspend fun execute(request: Request) {
        val response = okHttpClient.newCall(request).execute()
        println(response.body?.string())

//        return responseBodyToData(response.body?.string(), responseClass)
    }
}
