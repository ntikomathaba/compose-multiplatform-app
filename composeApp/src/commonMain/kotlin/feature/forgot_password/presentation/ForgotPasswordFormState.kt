package feature.forgot_password.presentation

data class ForgotPasswordFormState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val isFormSubmittedSuccessfully: Boolean = false,
)