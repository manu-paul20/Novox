package com.manu.novox.data.repository

import com.manu.novox.data.local.dao.SettingsDao
import com.manu.novox.data.local.entity.UserSettings
import com.manu.novox.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) : SettingsRepository{
    override suspend fun updateSettings(settings: UserSettings) {
        settingsDao.upsertSettings(settings)
    }

    override  fun getUserSettings(): UserSettings {
        return  settingsDao.getSetting()
    }

}