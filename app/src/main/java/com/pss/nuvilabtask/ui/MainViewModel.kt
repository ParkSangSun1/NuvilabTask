package com.pss.nuvilabtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss.nuvilabtask.common.NetworkStateObserver
import com.pss.nuvilabtask.common.RequestRetryQueue
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
    private val requestRetryQueue = RequestRetryQueue()

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
                if (isConnected) requestRetryQueue.retryAll()
            }
        }
    }

    fun getShortWeather(
        numOfRows: Int = 60,
        pageNo: Int = 1,
        latitude: Double,
        longitude: Double
    ) = viewModelScope.launch {
        val response = repository.getShortForecast(
            numOfRows = numOfRows,
            pageNo = pageNo,
            latitude = latitude,
            longitude = longitude
        )

        if (response != null) {
            //Network error로 작업이 실패 했을 때 retry 큐에 저장
            if (response.type == ErrorType.Network) requestRetryQueue.addRequest {
                repository.getShortForecast(
                    numOfRows = numOfRows,
                    pageNo = pageNo,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
    }

    fun setPermissionGrantedState(isGranted: Boolean) {
        _permissionGrantedState.value = isGranted
    }
}