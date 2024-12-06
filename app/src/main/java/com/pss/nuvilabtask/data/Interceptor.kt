package com.pss.nuvilabtask.data

import com.pss.nuvilabtask.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(
                "serviceKey",
                URLDecoder.decode(
                    BuildConfig.API_KEY,
                    StandardCharsets.UTF_8.toString()
                )
            )
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}