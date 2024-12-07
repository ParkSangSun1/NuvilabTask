package com.pss.nuvilabtask.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pss.nuvilabtask.model.WeatherType

@Entity(
    tableName = "weather",
)
data class WeatherInfoEntity(
    @PrimaryKey val time: String,
    val city: String,       //도시 정보 (서울특별시 등)
    val type: WeatherType,   //하늘 상태
    val t1h: String,        //기온
    val reh: String         //습도
)