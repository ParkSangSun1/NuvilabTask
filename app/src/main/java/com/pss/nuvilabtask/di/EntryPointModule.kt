package com.pss.nuvilabtask.di

import com.pss.nuvilabtask.core.worker.DataSyncWorkerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EntryPointModule {
    fun getDataSyncWorkFactory(): DataSyncWorkerFactory
}