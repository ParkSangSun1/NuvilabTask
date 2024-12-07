package com.pss.nuvilabtask.di

import com.pss.nuvilabtask.data.db.Database
import com.pss.nuvilabtask.data.db.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesWeatherDao(
        database: Database,
    ): WeatherDao = database.weatherDao()
}