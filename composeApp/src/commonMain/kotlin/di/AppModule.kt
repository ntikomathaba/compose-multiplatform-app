package di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import feature.forgot_password.di.forgotPasswordModule
import feature.login.di.loginModule
import feature.registration.di.registrationModule
import org.koin.dsl.module

fun appModule() = listOf(
    httpClientModule,
    loginModule,
    registrationModule,
    forgotPasswordModule,
    fireBase
)

val fireBase = module {
    single { Firebase.auth }
}