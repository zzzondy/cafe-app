package com.cafeapp.di.cart

import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.usecase.CreateCartForUserUseCase
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
    fun provideCreateCartForUserUseCase(cartRepository: CartRepository): CreateCartForUserUseCase =
        CreateCartForUserUseCase(cartRepository)
}