package com.cafeapp.di.cart

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.usecase.*
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
    fun provideAddFoodToCartUseCase(
        cartRepository: CartRepository,
        userManager: UserManager
    ): AddFoodToCartUseCase = AddFoodToCartUseCase(cartRepository, userManager)

    @ViewModelScoped
    @Provides
    fun provideObtainUserCartUseCase(
        cartRepository: CartRepository,
        userManager: UserManager
    ): ObtainUserCartUseCase = ObtainUserCartUseCase(cartRepository, userManager)

    @ViewModelScoped
    @Provides
    fun provideIncrementItemsCountUseCase(
        cartRepository: CartRepository,
        userManager: UserManager
    ): IncrementItemsCountUseCase = IncrementItemsCountUseCase(cartRepository, userManager)

    @ViewModelScoped
    @Provides
    fun provideDecrementItemsCountUseCase(
        cartRepository: CartRepository,
        userManager: UserManager
    ): DecrementItemsCountUseCase = DecrementItemsCountUseCase(cartRepository, userManager)

    @ViewModelScoped
    @Provides
    fun provideCalculateTotalUseCase(): CalculateTotalUseCase = CalculateTotalUseCase()

    @ViewModelScoped
    @Provides
    fun provideDeleteFoodFromUseCase(
        cartRepository: CartRepository,
        userManager: UserManager
    ): DeleteFoodFromCartUseCase = DeleteFoodFromCartUseCase(userManager, cartRepository)
}