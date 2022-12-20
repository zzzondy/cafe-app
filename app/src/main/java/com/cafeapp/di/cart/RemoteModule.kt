package com.cafeapp.di.cart

import com.cafeapp.data.cart.remote.RemoteCartRepository
import com.cafeapp.data.cart.remote.RemoteCartRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideRemoteCartRepository(firestore: FirebaseFirestore): RemoteCartRepository =
        RemoteCartRepositoryImpl(firestore)
}