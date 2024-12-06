package com.pss.nuvilabtask.model

import com.pss.nuvilabtask.model.WeatherType

data class WeatherUIInfo(
    val city: String,       //도시 정보 (서울특별시 등)
    val type: WeatherType,   //하늘 상태
    val t1h: String,        //기온
    val reh: String         //습도
)
