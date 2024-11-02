package xyz.ksharma.krail.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import xyz.ksharma.krail.network.BuildConfig.NSW_TRANSPORT_API_KEY
import javax.inject.Singleton

@Singleton
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            Request
                .Builder()
                .header("Authorization", "apikey $NSW_TRANSPORT_API_KEY")
                .header("accept", "application/x-google-protobuf")
                .url(chain.request().url)
                .build(),
        )
    }
}
