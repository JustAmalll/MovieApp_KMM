package dev.amal.movieapp.feature_movie_list.presentation

import dev.amal.movieapp.feature_movie_list.domain.model.Movie

data class MovieState(
    var isLoading: Boolean = false,
    val error: String? = null,
    val popularMovies: List<Movie> = emptyList()
)