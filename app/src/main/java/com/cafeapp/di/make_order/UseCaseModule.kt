package com.cafeapp.di.make_order

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.orders.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.orders.make_order.usecase.MakeOrderUseCase
import com.cafeapp.domain.orders.make_order.usecase.ObtainDeliveryMethodsUseCase
import com.cafeapp.domain.orders.make_order.usecase.ObtainPaymentMethodsUseCase
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
    fun provideObtainPaymentMethodsUseCase(makeOrderRepository: MakeOrderRepository): ObtainPaymentMethodsUseCase =
        ObtainPaymentMethodsUseCase(makeOrderRepository)

    @ViewModelScoped
    @Provides
    fun provideObtainDeliveryMethodsUseCase(makeOrderRepository: MakeOrderRepository): ObtainDeliveryMethodsUseCase =
        ObtainDeliveryMethodsUseCase(makeOrderRepository)

    @ViewModelScoped
    @Provides
    fun provideMakeOrderUseCase(
        userManager: UserManager,
        makeOrderRepository: MakeOrderRepository
    ): MakeOrderUseCase = MakeOrderUseCase(userManager, makeOrderRepository)
}