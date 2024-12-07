package com.pss.nuvilabtask.core

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object LocationManager {
    //first: latitude, second: longitude
    private val _locationInfo = MutableStateFlow<Pair<Double, Double>?>(null)
    val locationInfo = _locationInfo.asStateFlow()

    fun setLocationInfo(latitude: Double, longitude: Double){
        _locationInfo.value = Pair(latitude, longitude)
    }
}