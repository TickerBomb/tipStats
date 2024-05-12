import java.awt.Desktop
import java.net.URI

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun browseUrl(url: String) {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(url))
}
