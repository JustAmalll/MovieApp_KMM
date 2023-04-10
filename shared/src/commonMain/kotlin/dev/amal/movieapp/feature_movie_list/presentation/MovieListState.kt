package dev.amal.movieapp.feature_movie_list.presentation

import dev.amal.movieapp.feature_movie_list.domain.model.Genre
import dev.amal.movieapp.feature_movie_list.domain.model.Movie

data class MovieListState(
    var isLoading: Boolean = false,
    val searchText: String = "",
    val error: String? = null,
    val popularMovies: List<Movie> = emptyList(),
    val favoriteMovieIds: List<Long> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val searchedMovies: List<Movie> = emptyList(),
    val moviePage: Int = 1,
    val searchPage: Int = 1,
    val endReached: Boolean = false
)