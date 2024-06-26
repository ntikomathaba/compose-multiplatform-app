package feature.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import feature.login.presentation.components.LoginCard
import feature.navigation.LoginDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val viewModel = koinInject<LoginViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                LoginState.Success -> {
                    coroutineScope.launch {
                        delay(1500L) // Animation delay
                        navController.navigate(LoginDestination.HomeRoute.route)
                    }
                }

                is LoginState.Failure -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LoginCard(
                state = state,
                onEvent = { event ->
                    viewModel.onEvent(event)
                },
                onRegisterClick = {
                    navController.navigate(LoginDestination.RegistrationScreen.route)
                },
                onForgotPasswordClick = {
                    navController.navigate(LoginDestination.ForgotPasswordScreen.route)
                }
            )
        }
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginCard(
        state = LoginFormState(
            email = "abc@gmail.com",
            password = "abc@gmail.com",
            isLoading = true,
            isFormSubmittedSuccessfully = true
        ),
        onForgotPasswordClick = {}
    ) {

    }
}