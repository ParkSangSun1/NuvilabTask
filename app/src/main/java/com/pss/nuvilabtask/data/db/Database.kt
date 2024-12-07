package com.pss.nuvilabtask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        WeatherInfoEntity::class,
    ],
    version = 1,
)

abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}