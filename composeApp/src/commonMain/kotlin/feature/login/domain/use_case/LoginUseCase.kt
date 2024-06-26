package feature.login.domain.use_case

import dev.gitlive.firebase.auth.FirebaseAuth
import feature.login.domain.model.LoginUser
import feature.login.presentation.LoginFormState
import feature.login.presentation.LoginState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

class LoginUseCase(
    private val auth: FirebaseAuth
) {
    suspend  operator fun invoke(
        loginUser: LoginUser,
        eventChannel: Channel<LoginState>,
        updateState: (LoginFormState) -> Unit
    ){
        try {
            auth.signInWithEmailAndPassword(loginUser.email, loginUser.password)
            delay(5000L)
            eventChannel.send(LoginState.Success)
            updateState(
                LoginFormState(
                    email = loginUser.email,
                    password = loginUser.password,
                    isLoading = false,
                    isFormSubmittedSuccessfully = true
                )
            )
        } catch (e: Exception){
            updateState(
                LoginFormState(
                    email = loginUser.email,
                    password = loginUser.password,
                    isLoading = false,
                    isFormSubmittedSuccessfully = false
                )
            )
            e.message?.let { message ->
                eventChannel.send(LoginState.Failure(message))
            }
        }
    }
}