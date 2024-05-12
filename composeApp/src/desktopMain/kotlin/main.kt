import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    ApplicationComponent.init()
    val state = rememberWindowState(
        size = DpSize(1000.dp, 1500.dp),
        position = WindowPosition(300.dp, 300.dp)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "TipStats",
        state = state
    ) {
        App()
    }
}