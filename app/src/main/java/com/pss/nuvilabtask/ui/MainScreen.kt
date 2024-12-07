package com.pss.nuvilabtask.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pss.nuvilabtask.core.RequestPermissionUsingRememberLauncherForActivityResult
import com.pss.nuvilabtask.model.ErrorType
import com.pss.nuvilabtask.model.WeatherUIInfo

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val permissionGrantedState by viewModel.permissionGrantedState.collectAsState()
    val shortWeatherInfoState by viewModel.shortWeatherInfoState.collectAsState()
    val errorState by viewModel.errorState.collectAsState(null)
    val context = LocalContext.current

    RequestPermissionUsingRememberLauncherForActivityResult(
        onPermissionGranted = {
            viewModel.setPermissionGrantedState(true)
        },
        onPermissionDenied = {
            viewModel.setPermissionGrantedState(false)
            Toast.makeText(context, "위치 수집에 동의해 주세요", Toast.LENGTH_SHORT).show()
        },
    )

    if (permissionGrantedState) {
        GetLocation(
            onSuccess = {
                viewModel.setLocationInfo(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
//                viewModel.getShortWeather(
//                    latitude = it.latitude,
//                    longitude = it.longitude
//                )
            },
            onError = {
                Toast.makeText(context, "위치 정보를 불러오지 못했습니다", Toast.LENGTH_SHORT).show()
            }
        )
    }


    Box(modifier = Modifier.fillMaxSize()) {
        shortWeatherInfoState?.let {
            WeatherUIInfo(
                modifier = Modifier.align(Alignment.Center),
                info = it
            )
        }

        errorState?.let {
            ErrorInfo(
                modifier = Modifier.align(Alignment.BottomCenter),
                type = it
            )
        }
    }
}

@Composable
fun WeatherUIInfo(
    modifier: Modifier = Modifier,
    info: WeatherUIInfo
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

@Composable
fun ErrorInfo(
    modifier: Modifier = Modifier,
    type: ErrorType
) {
    Text(
        text = when (type) {
            is ErrorType.Http -> "서버에 장애가 발생했습니다"
            is ErrorType.Network -> "네트워크 연결을 확인해 주세요"
            is ErrorType.Timeout -> "요청 시간이 초과되었습니다"
            is ErrorType.Unknown -> "알 수 없는 오류가 발생했습니다"
        },
        fontSize = 23.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}