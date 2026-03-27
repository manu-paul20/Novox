package com.manu.novox.di

import com.manu.novox.data.repository.AccountRepositoryImpl
import com.manu.novox.data.repository.AuthRepositoryImpl
import com.manu.novox.data.repository.ChatRepositoryImpl
import com.manu.novox.data.repository.SettingsRepositoryImpl
import com.manu.novox.data.repository.InteractedUserRepositoryImpl
import com.manu.novox.domain.repository.AccountRepository
import com.manu.novox.domain.repository.AuthRepository
import com.manu.novox.domain.repository.ChatRepository
import com.manu.novox.domain.repository.SettingsRepository
import com.manu.novox.domain.repository.InteractedUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun provideAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun provideChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun provideInteractedUserRepository(
        interactedUserRepositoryImpl: InteractedUserRepositoryImpl
    ): InteractedUserRepository

    @Binds
    @Singleton
    abstract fun provideSettingRepository(
        settingRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository

}