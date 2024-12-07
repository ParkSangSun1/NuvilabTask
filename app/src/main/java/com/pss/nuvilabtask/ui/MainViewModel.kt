package com.pss.nuvilabtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss.nuvilabtask.core.LocationManager
import com.pss.nuvilabtask.core.NetworkStateObserver
import com.pss.nuvilabtask.core.RequestRetryQueue
import com.pss.nuvilabtask.model.ErrorType
import com.pss.nuvilabtask.model.WeatherUIInfo
import com.pss.nuvilabtask.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val networkStateObserver: NetworkStateObserver,
) : ViewModel() {

    private val _permissionGrantedState = MutableStateFlow<Boolean>(false)
    val permissionGrantedState = _permissionGrantedState.asStateFlow()

    private val _shortWeatherInfoState = MutableStateFlow<WeatherUIInfo?>(null)
    val shortWeatherInfoState = _shortWeatherInfoState.asStateFlow()

    val errorState = repository.errorState.asStateFlow()

    init {
        viewModelScope.launch {
            //room db data를 옵저버
            repository.getWeatherInfo().collectLatest {
                _shortWeatherInfoState.emit(it)
            }
        }

        viewModelScope.launch {
            //Network가 다시 연결되었을 때 실패한 request 실행
            networkStateObserver.isConnected.collectLatest { isConnected ->
                if (isConnected) RequestRetryQueue.retryAll()
            }
        }
    }

    fun setLocationInfo(
        latitude: Double,
        longitude: Double
    ){
        LocationManager.setLocationInfo(latitude, longitude)
    }

    fun setPermissionGrantedState(isGranted: Boolean) {
        _permissionGrantedState.value = isGranted
    }
}