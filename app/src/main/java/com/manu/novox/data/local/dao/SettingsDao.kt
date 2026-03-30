package com.manu.novox.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.manu.novox.data.local.entity.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Upsert
    suspend fun upsertSettings(settings: UserSettings)

    @Query("select * from settings where id=1")
    fun getSetting(): Flow<UserSettings>
}