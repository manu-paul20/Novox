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

    @Query("select * from settings")
    fun getSetting(): Flow<UserSettings>

    @Query("update settings set userName=:newKey where userName='key0'")
    suspend fun updateKey(newKey: String)
}