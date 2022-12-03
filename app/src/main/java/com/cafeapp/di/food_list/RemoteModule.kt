package com.cafeapp.di.food_list

import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepository
import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class RemoteModule {

    @ViewModelScoped
    @Provides
    fun provideRemoteFoodListRepository(fireStore: FirebaseFirestore): RemoteFoodListRepository =
        RemoteFoodListRepositoryImpl(fireStore)
}