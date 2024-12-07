package com.pss.nuvilabtask.ui

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@Composable
fun GetLocation(
    onSuccess: (Location) -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var locationError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect (Unit) {
        fetchLocation(fusedLocationClient, onSuccess = { location ->
            onSuccess(location)
        }, onError = { error ->
            onError(error)
        })
    }
}

@SuppressLint("MissingPermission")
private fun fetchLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onSuccess: (Location) -> Unit,
    onError: (String) -> Unit
) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onSuccess(location)
            } else {
                onError("위치를 가져올 수 없습니다.")
            }
        }
        .addOnFailureListener { exception ->
            onError("위치를 가져오는 중 오류 발생: ${exception.message}")
        }
}
