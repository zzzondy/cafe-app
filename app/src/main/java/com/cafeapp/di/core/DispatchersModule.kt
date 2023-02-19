package com.cafeapp.di.core

import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.core.providers.dispatchers.StandardDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DispatchersModule {

    @Singleton
    @Provides
    fun provideDispatchers(): DispatchersProvider = StandardDispatchers()
}