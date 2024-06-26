package feature.registration.domain.model

data class RegistrationUser(
    val email: String,
    val password: String,
    val confirmPassword: String
)
