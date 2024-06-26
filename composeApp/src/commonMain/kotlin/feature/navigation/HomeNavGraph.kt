package feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import feature.home.presentation.HomeScreen

@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeDestination.AppHomeScreen.route
    ){
        composable(
            route = HomeDestination.AppHomeScreen.route
        ){
            HomeScreen(
                navController = navController
            )
        }
    }
}

sealed class HomeDestination(val route: String) {
    data object AppHomeScreen : HomeDestination("app_home_screen")
}