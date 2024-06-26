package feature.registration.presentation

sealed interface  RegistrationEvent {
    data class OnEmailChanged(val email: String): RegistrationEvent
    data class OnPasswordChanged(val password: String): RegistrationEvent
    data class OnConfirmPasswordChanged(val confirmPassword: String): RegistrationEvent
    data object Submit: RegistrationEvent
}