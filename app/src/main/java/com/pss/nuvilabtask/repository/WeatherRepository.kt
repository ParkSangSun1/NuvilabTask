package com.pss.nuvilabtask.repository

import com.pss.nuvilabtask.data.db.WeatherInfoEntity
import com.pss.nuvilabtask.data.model.ApiResponseStatus
import com.pss.nuvilabtask.model.ErrorType
import com.pss.nuvilabtask.model.WeatherUIInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface WeatherRepository {
    val errorState: MutableStateFlow<ErrorType?>

    suspend fun getShortForecast(
        numOfRows: Int,
        pageNo: Int,
        latitude: Double,
        longitude: Double
    ): ApiResponseStatus.Error?

    fun getWeatherInfo(): Flow<WeatherUIInfo?>
}