import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.presentation.MyAppTheme
import di.appModule
import feature.login.di.loginModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import feature.navigation.LoginNavGraph
import feature.registration.di.registrationModule
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App(
    darkTheme: Boolean = false,
    dynamicCor: Boolean = false,
) = MyAppTheme {
    KoinApplication(
        application = {
            modules(appModule())
        }
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginNavGraph()
        }
    }
}