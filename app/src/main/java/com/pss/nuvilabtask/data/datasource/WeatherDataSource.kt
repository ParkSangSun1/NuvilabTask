package com.pss.nuvilabtask.data.datasource

import com.pss.nuvilabtask.data.model.WeatherResponse
import com.pss.nuvilabtask.data.model.ApiResponseStatus

interface WeatherDataSource {
    suspend fun getShortForecast(
        numOfRows : Int,
        pageNo : Int,
        baseDate : String,
        baseTime : String,
        nx : String,
        ny : String,
    ): ApiResponseStatus<WeatherResponse>
}