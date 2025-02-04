package com.yarendemirkaya.waterreminder.di

import android.content.Context
import com.yarendemirkaya.waterreminder.common.InternetChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideInternetChecker(@ApplicationContext context: Context): InternetChecker {
        return InternetChecker(context)
    }

}