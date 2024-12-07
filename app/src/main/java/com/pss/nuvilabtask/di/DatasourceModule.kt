package com.pss.nuvilabtask.di

import com.pss.nuvilabtask.data.datasource.LocalDbDataSource
import com.pss.nuvilabtask.data.datasource.LocalDbDataSourceImpl
import com.pss.nuvilabtask.data.datasource.WeatherDataSource
import com.pss.nuvilabtask.data.datasource.WeatherDataSourceImpl
import com.pss.nuvilabtask.repository.WeatherRepository
import com.pss.nuvilabtask.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    @Singleton
    internal abstract fun bindsWeatherDataSource(
        impl: WeatherDataSourceImpl
    ): WeatherDataSource

    @Binds
    @Singleton
    internal abstract fun bindsLocalDbDataSource(
        impl: LocalDbDataSourceImpl
    ): LocalDbDataSource
}