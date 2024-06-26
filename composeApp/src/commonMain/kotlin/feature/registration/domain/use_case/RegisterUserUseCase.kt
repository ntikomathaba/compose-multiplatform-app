package feature.registration.domain.use_case

import dev.gitlive.firebase.auth.FirebaseAuth
import feature.registration.domain.model.RegistrationUser
import feature.registration.presentation.RegistrationFormState
import feature.registration.presentation.RegistrationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

class RegisterUserUseCase(
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(
        registerUser: RegistrationUser,
        registrationChannel: Channel<RegistrationState>,
        updateState: (RegistrationFormState) -> Unit
    ) {
        try {
            auth.createUserWithEmailAndPassword(registerUser.email, registerUser.password)
            delay(5000L)
            registrationChannel.send(RegistrationState.Success)
            updateState(
                RegistrationFormState(
                    email = registerUser.email,
                    password = registerUser.password,
                    isLoading = false,
                    isFormSubmittedSuccessfully = true
                )
            )
        } catch (e: Exception) {
            updateState(
                RegistrationFormState(
                    email = registerUser.email,
                    password = registerUser.password,
                    confirmPassword = registerUser.confirmPassword,
                    isLoading = false,
                    serverError = e.message,
                    isFormSubmittedSuccessfully = false
                )
            )
            e.message?.let { message ->
                registrationChannel.send(RegistrationState.Failure(message))
            }
        }
    }
}