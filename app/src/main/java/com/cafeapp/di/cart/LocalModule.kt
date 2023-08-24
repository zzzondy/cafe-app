package com.cafeapp.di.cart

import android.content.Context
import com.cafeapp.data.cart.local.database.CartDatabase
import com.cafeapp.data.cart.local.repository.LocalCartRepository
import com.cafeapp.data.cart.local.repository.LocalCartRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalModule {

    @Singleton
    @Provides
    fun provideCartDatabase(@ApplicationContext context: Context): CartDatabase =
        CartDatabase.create(context)

    @Singleton
    @Provides
    fun provideLocalCartRepository(cartDatabase: CartDatabase): LocalCartRepository =
        LocalCartRepositoryImpl(cartDatabase.localCartDao)
}