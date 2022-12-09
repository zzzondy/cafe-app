package com.cafeapp.di.auth

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.usecase.ObserveCurrentUserUseCase
import com.cafeapp.domain.auth.usecase.SignInUserUseCase
import com.cafeapp.domain.auth.usecase.SignUpUserUseCase
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
    fun provideSignUpUserUseCase(userManager: UserManager): SignUpUserUseCase =
        SignUpUserUseCase(userManager)

    @ViewModelScoped
    @Provides
    fun provideSignInUserUseCase(userManager: UserManager): SignInUserUseCase =
        SignInUserUseCase(userManager)

    @ViewModelScoped
    @Provides
    fun provideObserveCurrentUserUseCase(userManager: UserManager): ObserveCurrentUserUseCase =
        ObserveCurrentUserUseCase(userManager)
}