package dev.amal.movieapp.core.utils

import dev.amal.movieapp.feature_favorite_movies.domain.repository.NetworkError
import io.ktor.utils.io.errors.*

fun Exception.handleNetworkException(): NetworkError {
    return when (this) {
        is IOException -> NetworkError.SERVICE_UNAVAILABLE
        else -> NetworkError.UNKNOWN_ERROR
    }
}

fun Int.handleHttpStatusCode(): NetworkError? {
    return when (this) {
        in 200..299 -> null
        in 400..499 -> NetworkError.CLIENT_ERROR
        500 -> NetworkError.SERVER_ERROR
        else -> NetworkError.UNKNOWN_ERROR
    }
}