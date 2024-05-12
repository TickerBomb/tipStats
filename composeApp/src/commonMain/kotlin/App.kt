import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

import ui.MainScreen
@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModule()) }) {
        MaterialTheme {
            MainScreen()
        }

    }
}

