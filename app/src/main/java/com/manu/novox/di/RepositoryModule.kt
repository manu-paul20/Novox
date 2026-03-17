package com.manu.novox.di

import com.manu.novox.data.repository.AuthRepositoryImpl
import com.manu.novox.data.repository.ChatRepositoryImpl
import com.manu.novox.data.repository.UserRepositoryImpl
import com.manu.novox.domain.repository.AuthRepository
import com.manu.novox.domain.repository.ChatRepository
import com.manu.novox.domain.repository.UserRepository
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
    abstract fun provideChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

}