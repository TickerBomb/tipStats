interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun browseUrl(url: String)