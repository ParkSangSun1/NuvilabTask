package com.pss.nuvilabtask.repository

import android.content.Context
import android.location.Geocoder
import com.pss.nuvilabtask.common.WeatherCommon
import com.pss.nuvilabtask.data.datasource.WeatherDataSource
import com.pss.nuvilabtask.data.model.ApiResponseStatus
import com.pss.nuvilabtask.data.model.toUiStatus
import com.pss.nuvilabtask.model.WeatherType
import com.pss.nuvilabtask.model.WeatherUIInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val datasource: WeatherDataSource,
    @ApplicationContext private val context: Context
) : WeatherRepository {
    override suspend fun getShortForecast(
        numOfRows: Int,
        pageNo: Int,
        latitude: Double,
        longitude: Double
    ): ApiResponseStatus<WeatherUIInfo> {
        val point = WeatherCommon.dfsXyConv(latitude, longitude)

        val response = datasource.getShortForecast(
            numOfRows = numOfRows,
            pageNo = pageNo,
            baseDate = WeatherCommon.getBaseDate(),
            baseTime = WeatherCommon.getBaseTime(),
            nx = point.x.toString(),
            ny = point.y.toString()
        )

        return response.toUiStatus(
            if (response is ApiResponseStatus.Success) {
                val skyState = getWeatherType(response.data.response.body.items.item.find { it.category == "PTY" }?.fcstValue, response.data.response.body.items.item.find { it.category == "SKY" }?.fcstValue)

                WeatherUIInfo(
                    city = getCityNameFromLocation(latitude, longitude),
                    type = skyState,
                    t1h = response.data.response.body.items.item.find { it.category == "T1H" }?.fcstValue ?: "",
                    reh = response.data.response.body.items.item.find { it.category == "REH" }?.fcstValue ?: "",
                )
            } else null
        )
    }

    private fun getCityNameFromLocation(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.KOREA)
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].locality ?: addresses[0].adminArea ?: "Unknown Location"
            } else {
                "Unknown"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error"
        }
    }

    private fun getWeatherType(pty: String?, sky: String?) : WeatherType {
        if (pty == null || sky == null) return WeatherType.NONE
        return when(pty){
            "0" -> {
                when(sky) {
                    "1" -> WeatherType.CLEAR
                    "3" -> WeatherType.PARTLY_CLOUDY
                    "4" -> WeatherType.OVERCAST
                    else -> WeatherType.NONE
                }
            }
            "1" -> WeatherType.RAIN
            "2" -> WeatherType.RAIN_AND_SNOW
            "3" -> WeatherType.SNOW
            "5" -> WeatherType.DRIZZLE
            "6" -> WeatherType.DRIZZLE_AND_FLURRY
            "7" -> WeatherType.FLURRY
            else -> WeatherType.NONE
        }
    }
}