package di

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private val kLogger = KotlinLogging.logger {}
val providehttpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }, contentType = ContentType.Any)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        kLogger.info { "Logger Ktor =>$message" }
//                        println("message: $message")
//                        logger.log()
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}