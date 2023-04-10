package dev.amal.movieapp.feature_favorite_movies.presentation

import dev.amal.movieapp.feature_favorite_movies.domain.model.FavoriteMovie

data class FavoriteMoviesState(
    var isLoading: Boolean = false,
    val error: String? = null,
    val favoriteMovies: List<FavoriteMovie> = emptyList(),
    val favoriteMoviesPage: Int = 1,
    val endReached: Boolean = false
)