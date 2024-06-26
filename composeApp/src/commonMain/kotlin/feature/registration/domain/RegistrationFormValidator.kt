package feature.registration.domain

import feature.registration.domain.model.RegistrationUser

object RegistrationFormValidator {
    fun validateForm(registrationUser: RegistrationUser): ValidationResult {
        var result = ValidationResult()

        if (registrationUser.email.isBlank()){
            result = result.copy(emailError = "Email Cannot Be Blank")
            return result
        }

        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if(!emailRegex.matches(registrationUser.email)) {
            result = result.copy(emailError = "This is not a valid email.")
        }

        if (registrationUser.password.isBlank()){
            result = result.copy(passwordError = "Password Cannot Be Blank")
        }

        if (registrationUser.password.length < 8 ){
            result = result.copy(passwordError = "Password Cannot Be Less Than 8 Characters")
        }

        if (registrationUser.confirmPassword.isBlank()){
            result = result.copy(confirmPasswordError = "This Cannot Be Blank")
        }

        if (registrationUser.password != registrationUser.confirmPassword){
            result = result.copy(confirmPasswordError = "Passwords Do Not Match")
        }

        return result
    }

     data class ValidationResult(
        val emailError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null
    )
}
