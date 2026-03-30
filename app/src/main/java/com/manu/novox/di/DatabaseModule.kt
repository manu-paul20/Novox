package com.manu.novox.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manu.novox.data.local.NovoxDatabase
import com.manu.novox.data.local.entity.UserSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope{
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    @Singleton
    fun provideNovoxDatabase(
       @ApplicationContext context: Context,
       @ApplicationScope scope: CoroutineScope,
       dbProvider: Provider<NovoxDatabase>

    ): NovoxDatabase{
       return Room.databaseBuilder(
            context = context,
            klass = NovoxDatabase::class.java,
            name = "NovoxDB"
        ).addCallback(object : RoomDatabase.Callback(){
           override fun onCreate(db: SupportSQLiteDatabase) {
               super.onCreate(db)
               scope.launch {
                   val dao = dbProvider.get().settingsDao
                   dao.upsertSettings(UserSettings())
               }
           }
        })
           .build()
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