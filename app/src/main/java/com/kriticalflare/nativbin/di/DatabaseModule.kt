package com.kriticalflare.nativbin.di

import android.content.Context
import androidx.room.Room
import com.kriticalflare.nativbin.history.data.HistoryDao
import com.kriticalflare.nativbin.history.data.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun providesHistoryDatabase(@ApplicationContext appContext: Context): HistoryDatabase {
        return Room.databaseBuilder(
            appContext,
            HistoryDatabase::class.java,
            "History_Database"
        ).build()
    }

    @Provides
    fun providesHistoryDao(historyDatabase: HistoryDatabase): HistoryDao {
        return historyDatabase.historyDao()
    }
}