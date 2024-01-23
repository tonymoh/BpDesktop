import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.composables.dbExample

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "SQLDelight Demonstrator") {
        dbExample()
    }
}