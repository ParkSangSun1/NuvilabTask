package com.pss.nuvilabtask.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pss.nuvilabtask.common.GetLocation
import com.pss.nuvilabtask.common.RequestPermissionUsingRememberLauncherForActivityResult
import com.pss.nuvilabtask.model.WeatherUIInfo

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val permissionGrantedState by viewModel.permissionGrantedState.collectAsState()
    val shortWeatherInfoState by viewModel.shortWeatherInfoState.collectAsState()

    RequestPermissionUsingRememberLauncherForActivityResult(
        onPermissionGranted = {
            viewModel.setPermissionGrantedState(true)
        },
        onPermissionDenied = {
            viewModel.setPermissionGrantedState(false)
        },
    )

    if(permissionGrantedState){
        GetLocation(
            onSuccess = {
                viewModel.getShortWeather(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            },
            onError = {

            }
        )
    }


    Box(modifier = Modifier.fillMaxSize()){
        shortWeatherInfoState?.let {
            WeatherUIInfo(
                modifier = Modifier.align(Alignment.Center),
                info = it
            )
        }
    }
}

@Composable
fun WeatherUIInfo(
    info: WeatherUIInfo,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "${info.city}", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "${info.t1h}ºC", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "${info.reh}%", fontSize = 24.sp)
    }
}