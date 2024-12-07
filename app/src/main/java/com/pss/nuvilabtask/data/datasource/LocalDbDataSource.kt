package com.pss.nuvilabtask.data.datasource

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pss.nuvilabtask.data.db.WeatherInfoEntity
import kotlinx.coroutines.flow.Flow

interface LocalDbDataSource {
    fun getWeatherInfo(): Flow<WeatherInfoEntity?>

    fun getAllWeatherInfo(): Flow<List<WeatherInfoEntity>>

    suspend fun saveWeather(entity: WeatherInfoEntity)

    suspend fun deleteWeather(entity: WeatherInfoEntity)

    suspend fun resetData(): Boolean
}