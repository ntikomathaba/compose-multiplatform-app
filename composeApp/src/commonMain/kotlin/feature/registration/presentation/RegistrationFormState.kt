package feature.registration.presentation

data class RegistrationFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isFormSubmittedSuccessfully: Boolean = false,
    val serverError: String? = null,
    val isLoading: Boolean = false
)
