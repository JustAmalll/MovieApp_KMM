package dev.amal.movieapp.core.data.remote

import dev.amal.movieapp.core.utils.Constants
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*

actual class HttpClientFactory {
    actual fun create(): HttpClient {
        return HttpClient(Darwin) {
            install(Logging)
            install(ContentNegotiation) { json() }
            install(DefaultRequest) {
                url {
                    url(Constants.BASE_URL)
                    parameters.append("api_key", Constants.API_KEY)
                }
            }
        }
    }
}