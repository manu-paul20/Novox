package com.manu.novox.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manu.novox.data.local.dao.InteractedUsersDao
import com.manu.novox.data.local.dao.MessageDao
import com.manu.novox.data.local.dao.SettingsDao
import com.manu.novox.data.local.dao.UserDao
import com.manu.novox.data.local.entity.InteractedUsers
import com.manu.novox.data.local.entity.Message
import com.manu.novox.data.local.entity.User
import com.manu.novox.data.local.entity.UserSettings

@Database(
    entities = [
        InteractedUsers::class,
        Message::class,
        User::class,
        UserSettings::class
    ],
    version = 1
)
abstract class NovoxDatabase(): RoomDatabase() {
    abstract val interactedUsersDao: InteractedUsersDao
    abstract val messageDao: MessageDao
    abstract val settingsDao: SettingsDao
    abstract val userDao: UserDao
}