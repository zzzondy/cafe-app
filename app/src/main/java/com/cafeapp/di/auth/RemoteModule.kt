package com.cafeapp.di.auth

import com.cafeapp.data.auth.remote.AuthRemoteDataSource
import com.cafeapp.data.auth.remote.AuthRemoteDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(firebaseAuth: FirebaseAuth): AuthRemoteDataSource =
        AuthRemoteDataSourceImpl(firebaseAuth)
}