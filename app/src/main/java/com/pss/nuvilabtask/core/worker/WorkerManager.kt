package com.pss.nuvilabtask.core.worker

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object WorkerManager {
    private val _workInfo = MutableStateFlow<WorkInfo.State?>(null)
    val workInfo: StateFlow<WorkInfo.State?> = _workInfo

    fun setWorkInfo(info: WorkInfo.State?){
        _workInfo.value = info
    }
}