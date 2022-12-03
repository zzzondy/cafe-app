package com.cafeapp.di.food_list

import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepository
import com.cafeapp.data.food_list.repository.FoodListRepositoryImpl
import com.cafeapp.domain.food_list.repository.FoodListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideFoodListRepository(remoteFoodListRepository: RemoteFoodListRepository): FoodListRepository =
        FoodListRepositoryImpl(remoteFoodListRepository)
}