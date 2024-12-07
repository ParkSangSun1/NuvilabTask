package com.pss.nuvilabtask.di

import android.content.Context
import androidx.room.Room
import com.pss.nuvilabtask.data.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): Database = Room.databaseBuilder(
        context,
        Database::class.java,
        "app-database",
    )
        .fallbackToDestructiveMigration()
        .build()
}