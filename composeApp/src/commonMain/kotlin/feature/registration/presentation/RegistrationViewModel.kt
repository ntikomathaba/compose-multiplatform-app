package feature.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.registration.domain.RegistrationFormValidator
import feature.registration.domain.model.RegistrationUser
import feature.registration.domain.use_case.RegisterUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class RegistrationViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val timber = logging("RegistrationEvent")

    private val _state = MutableStateFlow(RegistrationFormState())
    val state = _state.asStateFlow()

    private val registrationStateChannel = Channel<RegistrationState>()
    val registrationStateFlow = registrationStateChannel.receiveAsFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnEmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is RegistrationEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is RegistrationEvent.OnConfirmPasswordChanged -> {
                _state.update {
                    it.copy(
                        confirmPassword = event.confirmPassword
                    )
                }
            }

            is RegistrationEvent.Submit -> {

                val result = RegistrationFormValidator.validateForm(
                    registrationUser = RegistrationUser(
                        email = _state.value.email,
                        password = _state.value.password,
                        confirmPassword = _state.value.confirmPassword
                    )
                )

                val errors = listOfNotNull(
                    result.emailError,
                    result.passwordError,
                    result.confirmPasswordError
                )

                if (errors.isEmpty()) {
                    _state.update {
                        it.copy(
                            emailError = null,
                            passwordError = null,
                            confirmPasswordError = null,
                            isLoading = true
                        )
                    }
                    viewModelScope.launch {
                        register()
                    }
                } else {
                    _state.update {
                        it.copy(
                            emailError = result.emailError,
                            passwordError = result.passwordError,
                            confirmPasswordError = result.confirmPasswordError,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            registerUserUseCase(
                registerUser = RegistrationUser(
                    email = _state.value.email,
                    password = _state.value.password,
                    confirmPassword = _state.value.confirmPassword
                ),
                registrationChannel = registrationStateChannel,
                updateState = { newState -> _state.update { newState } }
            )
        }
    }
}

sealed interface RegistrationState {
    data object Success : RegistrationState
    data class Failure(val message: String) : RegistrationState
}
