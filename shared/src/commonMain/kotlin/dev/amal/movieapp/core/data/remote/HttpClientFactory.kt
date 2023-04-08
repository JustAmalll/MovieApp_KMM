package dev.amal.movieapp.core.data.remote

import io.ktor.client.*

expect class HttpClientFactory {
    fun create(): HttpClient
}