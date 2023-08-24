package com.cafeapp.di.food_list

import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepository
import com.cafeapp.data.food_list.repository.FoodListRepositoryImpl
import com.cafeapp.domain.food_list.repository.FoodListRepository
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
    fun provideFoodListRepository(remoteFoodListRepository: RemoteFoodListRepository): FoodListRepository =
        FoodListRepositoryImpl(remoteFoodListRepository)
}