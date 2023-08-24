package com.cafeapp.di.cart

import com.cafeapp.data.cart.local.repository.LocalCartRepository
import com.cafeapp.data.cart.remote.RemoteCartRepository
import com.cafeapp.data.cart.repository.CartRepositoryImpl
import com.cafeapp.domain.cart.repository.CartRepository
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
    fun provideCartRepository(
        remoteCartRepository: RemoteCartRepository,
        localCartRepository: LocalCartRepository
    ): CartRepository =
        CartRepositoryImpl(remoteCartRepository, localCartRepository)
}