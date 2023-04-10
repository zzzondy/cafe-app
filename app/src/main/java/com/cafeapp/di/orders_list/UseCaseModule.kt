package com.cafeapp.di.orders_list

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.orders.orders_list.repository.OrdersListRepository
import com.cafeapp.domain.orders.orders_list.usecase.ObtainOrdersForUserUseCase
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
    fun provideObtainUserOrdersUseCase(
        userManager: UserManager,
        ordersListRepository: OrdersListRepository
    ): ObtainOrdersForUserUseCase = ObtainOrdersForUserUseCase(userManager, ordersListRepository)
}