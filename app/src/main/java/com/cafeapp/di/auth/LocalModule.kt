package com.cafeapp.di.auth

import com.cafeapp.data.auth.local.AuthLocalDataSource
import com.cafeapp.data.auth.local.AuthLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalModule {

    @Singleton
    @Provides
    fun provideAuthLocalDataSource(): AuthLocalDataSource = AuthLocalDataSourceImpl()
}