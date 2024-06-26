package feature.login.di

import feature.login.domain.use_case.LoginUseCase
import feature.login.presentation.LoginViewModel
import org.koin.dsl.module

val loginModule = module {
    single { LoginUseCase(get()) }
    factory { LoginViewModel(get()) }
}