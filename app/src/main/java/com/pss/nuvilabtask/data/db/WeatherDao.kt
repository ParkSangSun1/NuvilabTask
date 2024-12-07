package com.pss.nuvilabtask.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather ORDER BY time DESC LIMIT 1")
    fun getWeatherInfo(): Flow<WeatherInfoEntity?>

    @Query("SELECT * FROM weather")
    fun getAllWeatherInfo(): Flow<List<WeatherInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(entity: WeatherInfoEntity)

    @Delete
    suspend fun deleteWeather(entity: WeatherInfoEntity)

    @Query("DELETE FROM weather")
    suspend fun resetData()
}