package com.manu.novox.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth


    @Provides
    @Singleton
    fun provideFirebaseDB() = Firebase.database

    @Provides
    @Singleton
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ) : CredentialManager{
        return CredentialManager.create(context)
    }
}