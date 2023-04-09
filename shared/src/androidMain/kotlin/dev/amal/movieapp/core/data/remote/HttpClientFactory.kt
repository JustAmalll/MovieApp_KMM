package dev.amal.movieapp.core.data.remote

import dev.amal.movieapp.core.utils.Constants.API_KEY
import dev.amal.movieapp.core.utils.Constants.BASE_URL
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*

actual class HttpClientFactory {
    actual fun create(): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging)
            install(ContentNegotiation) { json() }
            install(DefaultRequest) {
                url {
                    url(BASE_URL)
                    parameters.append("api_key", API_KEY)
                }
            }
        }
    }
}