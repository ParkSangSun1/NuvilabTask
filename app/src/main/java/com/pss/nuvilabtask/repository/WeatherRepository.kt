package com.pss.nuvilabtask.repository

import com.pss.nuvilabtask.data.db.WeatherInfoEntity
import com.pss.nuvilabtask.data.model.ApiResponseStatus
import com.pss.nuvilabtask.model.WeatherUIInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getShortForecast(
        numOfRows: Int,
        pageNo: Int,
        latitude: Double,
        longitude: Double
    ): ApiResponseStatus.Error?

    fun getWeatherInfo(): Flow<WeatherUIInfo?>
}