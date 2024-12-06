package com.pss.nuvilabtask.data.datasource

import com.pss.nuvilabtask.data.api.WeatherApi
import com.pss.nuvilabtask.data.model.WeatherResponse
import com.pss.nuvilabtask.common.safeApiCallResponse
import com.pss.nuvilabtask.data.model.ApiResponseStatus
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(
    private val api: WeatherApi,
): WeatherDataSource {
    override suspend fun getShortForecast(
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        nx: String,
        ny: String
    ): ApiResponseStatus<WeatherResponse> {
        return safeApiCallResponse { api.getShortForecast(numOfRows = numOfRows, pageNo = pageNo, baseDate = baseDate, baseTime = baseTime, nx = nx, ny = ny) }
    }
}