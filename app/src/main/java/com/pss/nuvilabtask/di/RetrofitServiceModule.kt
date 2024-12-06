package com.pss.nuvilabtask.di

import com.pss.nuvilabtask.BuildConfig
import com.pss.nuvilabtask.data.Interceptor
import com.pss.nuvilabtask.data.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.security.KeyStore
import javax.inject.Singleton
import javax.net.ssl.TrustManagerFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {

    @Provides
    @Singleton
    internal fun providesWeatherApiService(
    ): WeatherApi {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder
                .addInterceptor(Interceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        }

        val okHttpClient = okHttpClientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(
                BuildConfig.BASE_URL
            )
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create()
    }
}