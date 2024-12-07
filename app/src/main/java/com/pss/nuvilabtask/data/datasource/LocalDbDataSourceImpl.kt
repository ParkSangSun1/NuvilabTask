package com.pss.nuvilabtask.data.datasource

import com.pss.nuvilabtask.data.db.WeatherDao
import com.pss.nuvilabtask.data.db.WeatherInfoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDbDataSourceImpl @Inject constructor(
    private val dao: WeatherDao
): LocalDbDataSource {
    override fun getWeatherInfo(): Flow<WeatherInfoEntity?> {
        return dao.getWeatherInfo()
    }

    override fun getAllWeatherInfo(): Flow<List<WeatherInfoEntity>> {
        return dao.getAllWeatherInfo()
    }

    override suspend fun saveWeather(entity: WeatherInfoEntity) {
        return dao.saveWeather(entity)
    }

    override suspend fun deleteWeather(entity: WeatherInfoEntity) {
        return dao.deleteWeather(entity)
    }

    override suspend fun resetData(): Boolean {
        return try {
            dao.resetData()
            true
        }catch (e: Exception){
            false
        }
    }
}