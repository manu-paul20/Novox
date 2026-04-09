package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun updateSettings(settings: UserSettings)

    fun getUserSettings(): UserSettings
}