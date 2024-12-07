package com.pss.nuvilabtask.core.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.pss.nuvilabtask.core.LocationManager
import com.pss.nuvilabtask.repository.WeatherRepository
import javax.inject.Inject

class DataSyncWorkerFactory @Inject constructor(
    private val repository: WeatherRepository,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return if (workerClassName == DataSyncWorker::class.java.name) {
            DataSyncWorker(appContext, workerParameters, LocationManager.locationInfo.value?.first ?: 0.0, LocationManager.locationInfo.value?.second ?: 0.0, repository)
        } else {
            null
        }
    }
}