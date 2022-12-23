package com.cafeapp.di.auth

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.usecase.*
import com.cafeapp.domain.auth.usecase.validation.*
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

    @ViewModelScoped
    @Provides
    fun provideSignOutUserUseCase(userManager: UserManager): SignOutUserUseCase =
        SignOutUserUseCase(userManager)

    @ViewModelScoped
    @Provides
    fun provideCheckUserExistsUseCase(authRepository: AuthRepository): CheckUserExistsUseCase =
        CheckUserExistsUseCase(authRepository)


    @ViewModelScoped
    @Provides
    fun provideValidateEmailUseCase(): ValidateEmailUseCase = ValidateEmailUseCase()


    @ViewModelScoped
    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase = ValidatePasswordUseCase()

    @ViewModelScoped
    @Provides
    fun provideValidateFirstNameUseCase(): ValidateFirstNameUseCase = ValidateFirstNameUseCase()

    @ViewModelScoped
    @Provides
    fun provideValidateLastNameUseCase(): ValidateLastNameUseCase = ValidateLastNameUseCase()

    @ViewModelScoped
    @Provides
    fun provideValidatePhoneNumberUseCase(): ValidatePhoneNumberUseCase = ValidatePhoneNumberUseCase()
}