package feature.forgot_password.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.forgot_password.domain.ForgotPasswordValidator
import feature.forgot_password.domain.model.ForgotPasswordUser
import feature.forgot_password.domain.use_case.ForgotPasswordUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
): ViewModel()  {
    private val timber = logging("ForgotPasswordEvent")

    private val _state = MutableStateFlow(ForgotPasswordFormState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<ForgotPasswordState>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onEvent(event: ForgotPasswordEvent){
        when (event){
            is ForgotPasswordEvent.OnEmailChanged -> {
                timber.d { "changing email" }
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            ForgotPasswordEvent.Submit -> {
                timber.d { "submitting form" }
                val result = ForgotPasswordValidator.validateForm(
                    forgotPasswordUser = ForgotPasswordUser(
                        email = _state.value.email,
                    )
                )

                val errors = listOfNotNull(
                    result.emailError,
                )

                if (errors.isEmpty()) {
                    _state.update {
                        it.copy(
                            emailError = null,
                            isLoading = true
                        )
                    }
                    viewModelScope.launch {
                        forgotPassword()
                    }
                } else {
                    _state.update {
                        it.copy(
                            emailError = result.emailError,
                        )
                    }
                }

            }
        }
    }

    private fun forgotPassword(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            forgotPasswordUseCase(
                forgotPasswordUser = ForgotPasswordUser(
                    email = _state.value.email,
                ),
                eventChannel = eventChannel,
                updateState = { newState -> _state.update { newState } }
            )
        }
    }
}

sealed interface ForgotPasswordState {
    data object Success : ForgotPasswordState
    data class Failure(val message: String) : ForgotPasswordState
}