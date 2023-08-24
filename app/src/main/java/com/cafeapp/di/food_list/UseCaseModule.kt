package com.cafeapp.di.food_list

import com.cafeapp.domain.food_list.repository.FoodListRepository
import com.cafeapp.domain.food_list.usecase.GetPagedFoodListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetPagedFoodListUseCase(foodListRepository: FoodListRepository): GetPagedFoodListUseCase =
        GetPagedFoodListUseCase(foodListRepository)
}