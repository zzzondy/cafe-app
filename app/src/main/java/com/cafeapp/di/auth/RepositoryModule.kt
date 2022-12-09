package com.cafeapp.di.auth

import com.cafeapp.data.auth.local.AuthLocalDataSource
import com.cafeapp.data.auth.remote.AuthRemoteDataSource
import com.cafeapp.data.auth.repository.AuthRepositoryImpl
import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.manager.UserManagerImpl
import com.cafeapp.domain.auth.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSource, authLocalDataSource)

    @Singleton
    @Provides
    fun provideUserManager(authRepository: AuthRepository): UserManager =
        UserManagerImpl(authRepository)
}