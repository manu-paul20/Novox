package com.manu.novox.di

import android.content.Context
import androidx.room.Room
import com.manu.novox.data.local.NovoxDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{

    fun provideNovoxDatabase(
       @ApplicationContext context: Context
    ): NovoxDatabase{
       return Room.databaseBuilder(
            context = context,
            klass = NovoxDatabase::class.java,
            name = "NovoxDB"
        ).build()
    }

    fun provideUserDao(
        db: NovoxDatabase
    ) = db.userDao

    fun provideMessageDao(
        db: NovoxDatabase
    ) = db.messageDao

    fun provideSettingDao(
        db: NovoxDatabase
    ) = db.settingsDao

    fun provideInteractedUsersDao(
        db: NovoxDatabase
    ) = db.interactedUsersDao

}