package feature.registration.di

import feature.registration.domain.use_case.RegisterUserUseCase
import feature.registration.presentation.RegistrationViewModel
import org.koin.dsl.module

val registrationModule = module {
    single { RegisterUserUseCase(get()) }
    factory { RegistrationViewModel(get()) }
}