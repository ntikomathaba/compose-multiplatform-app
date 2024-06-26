package za.co.compose.multiplatform

import App
import Greeting
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import core.presentation.SystemAppearance
import kotlinx.coroutines.launch
import ui.theme.spacing

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Firebase.initialize(this)
        setContent {
            App(
                darkTheme = false,
                dynamicCor = false
            )

        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}