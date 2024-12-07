package com.pss.nuvilabtask.data

import android.util.Log
import com.pss.nuvilabtask.BuildConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import kotlin.math.pow
import kotlin.random.Random

class Interceptor : Interceptor {
    private val keyParameter = "serviceKey"
    private var attempt = 0
    private val maxRetries = 3
    private val minDelay = 1000L
    private val maxDelay = 5000L

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(
                keyParameter,
                URLDecoder.decode(
                    BuildConfig.API_KEY,
                    StandardCharsets.UTF_8.toString()
                )
            )
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        var response: Response? = null

        while (attempt < maxRetries) {
            try {
                response = chain.proceed(newRequest)
                if (response.isSuccessful) return response
            } catch (e: Exception) {
                Log.e("Interceptor", "Network request retry failed: ${e.message}")
            }

            attempt++

            val currentMaxDelay =
                (minDelay * 2.0.pow(attempt.toDouble())).toLong().coerceAtMost(maxDelay)
            val randomDelay = Random.nextLong(minDelay, currentMaxDelay)
            runBlocking { delay(randomDelay) }
        }

        return chain.proceed(newRequest)
    }
}