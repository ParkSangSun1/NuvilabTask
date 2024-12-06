package com.pss.nuvilabtask.common

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
