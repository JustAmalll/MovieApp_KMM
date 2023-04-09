package dev.amal.movieapp.feature_movie_list.presentation

import dev.amal.movieapp.feature_movie_list.domain.model.Genre
import dev.amal.movieapp.feature_movie_list.domain.model.Movie

data class MovieState(
    var isLoading: Boolean = false,
    val error: String? = null,
    val popularMovies: List<Movie> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val page: Int = 1,
    val endReached: Boolean = false
)