package feature.forgot_password.domain.use_case

import dev.gitlive.firebase.auth.FirebaseAuth
import feature.forgot_password.domain.model.ForgotPasswordUser
import feature.forgot_password.presentation.ForgotPasswordFormState
import feature.forgot_password.presentation.ForgotPasswordState
import feature.login.presentation.LoginFormState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

class ForgotPasswordUseCase(
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(
        forgotPasswordUser: ForgotPasswordUser,
        eventChannel: Channel<ForgotPasswordState>,
        updateState: (ForgotPasswordFormState) -> Unit
    ) {
        try {
            auth.sendPasswordResetEmail(forgotPasswordUser.email)
            delay(5000L)
            eventChannel.send(ForgotPasswordState.Success)
            updateState(
                ForgotPasswordFormState(
                    email = forgotPasswordUser.email,
                    isLoading = false,
                    isFormSubmittedSuccessfully = true
                )
            )
        } catch (e: Exception) {
            updateState(
                ForgotPasswordFormState(
                    email = forgotPasswordUser.email,
                    isLoading = false,
                    isFormSubmittedSuccessfully = false
                )
            )
            e.message?.let { message ->
                eventChannel.send(ForgotPasswordState.Failure(message))
            }
        }
    }

}

