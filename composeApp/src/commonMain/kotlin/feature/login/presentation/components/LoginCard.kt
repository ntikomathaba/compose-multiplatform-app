package feature.login.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import feature.login.presentation.LoginEvent
import feature.login.presentation.LoginFormState

@Composable
fun LoginCard(
    state: LoginFormState = LoginFormState(),
    onEvent: (LoginEvent) -> Unit = {},
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Login", style = MaterialTheme.typography.headlineLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))


            val isEmailError = state.emailError != null
            OutlinedTextField(
                value = state.email,
                maxLines = 1,
                onValueChange = {
                    onEvent(LoginEvent.OnEmailChanged(it))
                },
                isError = isEmailError,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            if (isEmailError && state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }


            val isPasswordError = state.passwordError != null
            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = state.password,
                isError = isPasswordError,
                onValueChange = {
                    onEvent(LoginEvent.OnPasswordChanged(it))
                },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
            )

            if (isPasswordError && state.passwordError != null) {
                AnimatedVisibility(true) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(
                                onClick = onForgotPasswordClick
                            ) {
                                Text(
                                    text = "Forgot Password?"
                                )
                            }
                        }
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val buttonCounter = remember {
                mutableStateOf(0)
            }
            Button(
                onClick = { onEvent(LoginEvent.Submit) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text("Login")

                if (state.isLoading) {
                    Row {
                        Spacer(modifier = Modifier.width(4.dp))
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                if (buttonCounter.value >= 1 && state.isFormSubmittedSuccessfully && !state.isLoading) {
                    AnimatedVisibility(visible = true) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Check Mark",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Don't have an account? Register")
            }
        }
    }
}