package com.pss.nuvilabtask.repository

import com.pss.nuvilabtask.data.model.ApiResponseStatus
import com.pss.nuvilabtask.model.WeatherUIInfo

interface WeatherRepository {
    suspend fun getShortForecast(
        numOfRows : Int,
        pageNo : Int,
        latitude: Double,
        longitude: Double
    ): ApiResponseStatus<WeatherUIInfo>
}