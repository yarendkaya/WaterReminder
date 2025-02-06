package com.yarendemirkaya.waterreminder.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.yarendemirkaya.waterreminder.common.InternetChecker
import com.yarendemirkaya.waterreminder.data.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideInternetChecker(@ApplicationContext context: Context): InternetChecker {
        return InternetChecker(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository { // bunu repository module acarak orada yapmak?
        return AuthRepository(auth)
    }

}