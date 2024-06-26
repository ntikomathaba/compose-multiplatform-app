package feature.forgot_password.domain

import feature.forgot_password.domain.model.ForgotPasswordUser
import feature.login.domain.FormValidator

object ForgotPasswordValidator {
    fun validateForm(forgotPasswordUser: ForgotPasswordUser): ValidationResult {
        var result = ValidationResult()

        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if(!emailRegex.matches(forgotPasswordUser.email)) {
            result = result.copy(emailError = "This is not a valid email.")
        }

        if (forgotPasswordUser.email.isBlank()){
            result = result.copy(emailError = "Email Cannot Be Blank")
        }

        return result
    }

    data class ValidationResult(
        val emailError: String? = null,
    )
}