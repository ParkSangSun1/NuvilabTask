package com.pss.nuvilabtask.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss.nuvilabtask.data.model.ApiResponseStatus
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
    private val repository: WeatherRepository
): ViewModel() {

    private val _permissionGrantedState = MutableStateFlow<Boolean>(false)
    val permissionGrantedState = _permissionGrantedState.asStateFlow()

    private val _shortWeatherInfoState = MutableStateFlow<WeatherUIInfo?>(null)
    val shortWeatherInfoState = _shortWeatherInfoState.asStateFlow()

    private val _errorState = MutableStateFlow<ErrorType?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWeatherInfo().collectLatest {
                _shortWeatherInfoState.emit(it)
            }
        }
    }

    fun getShortWeather(
        numOfRows : Int = 60,
        pageNo : Int = 1,
        latitude: Double,
        longitude: Double
    ) = viewModelScope.launch {
        val response = repository.getShortForecast(
            numOfRows = numOfRows,
            pageNo = pageNo,
            latitude = latitude,
            longitude = longitude
        )

        if (response != null) _errorState.value = response.type
    }

    fun setPermissionGrantedState(isGranted: Boolean){
        _permissionGrantedState.value = isGranted
    }
}