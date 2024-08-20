package xyz.ksharma.krail.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            Request
                .Builder()
                .addHeader("accept", "application/x-google-protobuf")
                .addHeader("Authorization", "apikey $API_KEY")
                .url(chain.request().url)
                .build()
        )
    }

    companion object {
        const val API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJPNkliSzI3NU" +
                "VPNzdPM3NjRjlKSWpKZFF3YXZ1dEJMWmVuekRYRzgxUTRVIiwiaWF0IjoxNzIxMDQwMjM1fQ.fO8B3P0" +
                "TEh71_imakg66Bs9TPdijW77TpaKw470cu-o"
    }
}
