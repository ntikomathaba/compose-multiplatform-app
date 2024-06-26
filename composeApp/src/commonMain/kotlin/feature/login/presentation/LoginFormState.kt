package feature.login.presentation

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isFormSubmittedSuccessfully: Boolean = false,
)