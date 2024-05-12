import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun browseUrl(url: String) {
    logger.info { "fix me!!!! url: $url" }
    // todo fix me
}