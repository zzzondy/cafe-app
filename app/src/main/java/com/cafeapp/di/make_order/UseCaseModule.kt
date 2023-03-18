package com.cafeapp.di.make_order

import com.cafeapp.domain.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.make_order.use_case.ObtainDeliveryMethodsUseCase
import com.cafeapp.domain.make_order.use_case.ObtainPaymentMethodsUseCase
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
}