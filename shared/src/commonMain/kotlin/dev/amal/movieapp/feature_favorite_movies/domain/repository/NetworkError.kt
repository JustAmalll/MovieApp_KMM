package dev.amal.movieapp.feature_favorite_movies.domain.repository

enum class NetworkError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class NetworkException(val error: NetworkError) : Exception("$error")