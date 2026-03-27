package com.manu.novox.di

import android.content.Context
import androidx.room.Room
import com.manu.novox.data.local.NovoxDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{

    @Provides
    @Singleton
    fun provideNovoxDatabase(
       @ApplicationContext context: Context
    ): NovoxDatabase{
       return Room.databaseBuilder(
            context = context,
            klass = NovoxDatabase::class.java,
            name = "NovoxDB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(
        db: NovoxDatabase
    ) = db.userDao

    @Provides
    @Singleton
    fun provideMessageDao(
        db: NovoxDatabase
    ) = db.messageDao

    @Provides
    @Singleton
    fun provideSettingDao(
        db: NovoxDatabase
    ) = db.settingsDao

    @Provides
    @Singleton
    fun provideInteractedUsersDao(
        db: NovoxDatabase
    ) = db.interactedUsersDao

}