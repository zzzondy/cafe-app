package com.cafeapp.di.food_list

import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepository
import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepositoryImpl
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
    fun provideRemoteFoodListRepository(fireStore: FirebaseFirestore): RemoteFoodListRepository =
        RemoteFoodListRepositoryImpl(fireStore)
}