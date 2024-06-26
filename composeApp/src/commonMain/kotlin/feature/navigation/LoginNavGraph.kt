package feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import feature.forgot_password.presentation.ForgotPasswordScreen
import feature.login.presentation.LoginScreen
import feature.registration.presentation.RegistrationScreen

@Composable
fun LoginNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination.LoginScreen.route
    ){
        composable(
            route = LoginDestination.LoginScreen.route
        ){
            LoginScreen(
                navController = navController
            )
        }

        composable(
            route = LoginDestination.RegistrationScreen.route
        ){
            RegistrationScreen(
                navController = navController
            )
        }

        composable(
            route = LoginDestination.ForgotPasswordScreen.route
        ){
            ForgotPasswordScreen(
                navController = navController
            )
        }

        composable(
            route = LoginDestination.HomeRoute.route
        ){
            HomeNavGraph()
        }
    }
}

sealed class LoginDestination(val route: String) {
    data object LoginScreen : LoginDestination("login_screen")
    data object RegistrationScreen : LoginDestination("registration_screen")
    data object ForgotPasswordScreen : LoginDestination("forgot_password_screen")
    data object HomeRoute: LoginDestination("home_route")
}