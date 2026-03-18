package com.manu.novox.data.local.dao

import androidx.room.Query
import androidx.room.Update
import com.manu.novox.data.local.entity.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsDao {
    @Update
    suspend fun updateSettings(settings: UserSettings)

    @Query("select * from settings")
    fun getSetting(): Flow<UserSettings>
}