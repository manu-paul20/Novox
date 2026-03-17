package com.manu.novox.di

import com.manu.novox.domain.repository.AuthRepository
import com.manu.novox.domain.repository.ChatRepository
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

    ): AuthRepository

    @Binds
    @Singleton
    abstract fun provideChatRepository(

    ): ChatRepository
}