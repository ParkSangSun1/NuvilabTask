package com.pss.nuvilabtask.core.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.pss.nuvilabtask.core.RequestRetryQueue
import com.pss.nuvilabtask.model.ErrorType
import com.pss.nuvilabtask.repository.WeatherRepository

class DataSyncWorker(
    context: Context,
    params: WorkerParameters,
    private val latitude: Double,
    private val longitude: Double,
    private val weatherRepository: WeatherRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val response = weatherRepository.getShortForecast(
            pageNo = 1,
            latitude = latitude,
            longitude = longitude,
            numOfRows = 60
        )
        return if (response == null) Result.success()
        else {
            //Network error로 작업이 실패 했을 때 retry 큐에 저장
            if (response.type == ErrorType.Network) RequestRetryQueue.addRequest {
                weatherRepository.getShortForecast(
                    pageNo = 1,
                    latitude = latitude,
                    longitude = longitude,
                    numOfRows = 60
                )
            }
            Result.failure()
        }
    }
}