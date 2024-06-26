package feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.login.domain.FormValidator
import feature.login.domain.model.LoginUser
import feature.login.domain.use_case.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class LoginViewModel(
    val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val timber = logging("LoginEvent")

    private val _state = MutableStateFlow(LoginFormState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginState>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                timber.d { "changing email" }
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is LoginEvent.OnPasswordChanged -> {
                timber.d { "changing password" }
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            LoginEvent.Submit -> {
                timber.d { "submitting form" }
                val result = FormValidator.validateForm(
                    loginUser = LoginUser(
                        email = _state.value.email,
                        password = _state.value.password
                    )
                )

                val errors = listOfNotNull(
                    result.emailError,
                    result.passwordError
                )

                if (errors.isEmpty()) {
                    _state.update {
                        it.copy(
                            emailError = null,
                            passwordError = null,
                            isLoading = true
                        )
                    }
                    viewModelScope.launch {
                        login()
                    }
                } else {
                    _state.update {
                        it.copy(
                            emailError = result.emailError,
                            passwordError = result.passwordError
                        )
                    }
                }

            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginUseCase(
                loginUser = LoginUser(
                    email = _state.value.email,
                    password = _state.value.password
                ),
                eventChannel = eventChannel,
                updateState = { newState -> _state.update { newState } }
            )
        }
    }
}

sealed interface LoginState {
    data object Success : LoginState
    data class Failure(val message: String) : LoginState
}