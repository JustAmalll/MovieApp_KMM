package dev.amal.movieapp.core.data.remote

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*

actual class HttpClientFactory {
    actual fun create(): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging)
            install(ContentNegotiation) { json() }
        }
    }
}