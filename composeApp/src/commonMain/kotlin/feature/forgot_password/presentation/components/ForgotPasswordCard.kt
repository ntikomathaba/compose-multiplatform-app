package feature.forgot_password.presentation.components

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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.forgot_password.presentation.ForgotPasswordEvent
import feature.forgot_password.presentation.ForgotPasswordFormState

@Composable
fun ForgotPasswordCard(
    state: ForgotPasswordFormState = ForgotPasswordFormState(),
    onEvent: (ForgotPasswordEvent) -> Unit = {},
    modifier: Modifier = Modifier
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
            ){
                Text("Forgot Password", style = MaterialTheme.typography.headlineLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isEmailError = state.emailError != null
            OutlinedTextField(
                value = state.email,
                maxLines = 1,
                onValueChange = {
                    onEvent(ForgotPasswordEvent.OnEmailChanged(it))
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

            val buttonCounter = remember {
                mutableStateOf(0)
            }
            Button(
                onClick = {
                    buttonCounter.value++
                    onEvent(ForgotPasswordEvent.Submit)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Forgot Password")
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
        }
    }

}