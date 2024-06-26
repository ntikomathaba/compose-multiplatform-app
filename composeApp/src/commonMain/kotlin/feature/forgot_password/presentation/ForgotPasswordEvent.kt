package feature.forgot_password.presentation

sealed interface ForgotPasswordEvent {
    data class OnEmailChanged(val email: String): ForgotPasswordEvent
    data object Submit: ForgotPasswordEvent
}