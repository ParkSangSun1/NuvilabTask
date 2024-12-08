package com.pss.nuvilabtask

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.pss.nuvilabtask.core.LocationManager
import com.pss.nuvilabtask.core.worker.DataSyncWorker
import com.pss.nuvilabtask.core.worker.WorkerManager
import com.pss.nuvilabtask.di.EntryPointModule
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    private val workName = "DataSync"
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private val applicationMainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            LocationManager.locationInfo.collectLatest {
                if (it != null) startDataSyncWorker()
            }
        }
    }

    private fun startDataSyncWorker() {
        val workRequest = PeriodicWorkRequestBuilder<DataSyncWorker>(15, TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            workName,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )

        applicationMainScope.launch {
            workManager.getWorkInfoByIdLiveData(workRequest.id).observeForever { workInfo ->
                WorkerManager.setWorkInfo(workInfo.state)
            }
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val workerFactory = EntryPoints.get(
            this,
            EntryPointModule::class.java
        ).getDataSyncWorkFactory()

        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}