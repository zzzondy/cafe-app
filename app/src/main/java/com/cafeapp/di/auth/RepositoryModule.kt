package com.cafeapp.di.auth

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
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSource)

    @Singleton
    @Provides
    fun provideUserManager(
        authRepository: AuthRepository
    ): UserManager =
        UserManagerImpl(authRepository)
}