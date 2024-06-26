package feature.forgot_password.di

import feature.forgot_password.domain.use_case.ForgotPasswordUseCase
import feature.forgot_password.presentation.ForgotPasswordViewModel
import org.koin.dsl.module

val forgotPasswordModule = module {
    single { ForgotPasswordUseCase(get()) }
    factory { ForgotPasswordViewModel(get()) }
}