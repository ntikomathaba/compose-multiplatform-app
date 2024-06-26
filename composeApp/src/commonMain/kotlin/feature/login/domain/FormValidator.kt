package feature.login.domain

import feature.login.domain.model.LoginUser

object FormValidator {

    fun validateForm(loginUser: LoginUser): ValidationResult {
        var result = ValidationResult()

        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if(!emailRegex.matches(loginUser.email)) {
            result = result.copy(emailError = "This is not a valid email.")
        }

        if (loginUser.email.isBlank()){
            result = result.copy(emailError = "Email Cannot Be Blank")
        }

        if (loginUser.password.length < 8 ){
            result = result.copy(passwordError = "Password Cannot Be Less Than 8 Characters")
        }

        return result
    }

    data class ValidationResult(
        val emailError: String? = null,
        val passwordError: String? = null
    )
}

