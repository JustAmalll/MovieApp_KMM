package dev.amal.movieapp.feature_movie_list.presentation

import dev.amal.movieapp.feature_movie_list.domain.model.Genre
import dev.amal.movieapp.feature_movie_list.domain.model.Movie

data class MovieListState(
    val isMoviesLoading: Boolean = true,
    val isGenresLoading: Boolean = true,
    val isNextItemsLoading: Boolean = false,
    val searchText: String = "",
    val error: String? = null,
    val popularMovies: List<Movie> = emptyList(),
    val favoriteMovieIds: List<Long> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val searchedMovies: List<Movie> = emptyList(),
    val moviePage: Int = 1,
    val searchPage: Int = 1,
    val popularMoviesEndReached: Boolean = false,
    val searchedMoviesEndReached: Boolean = false
)