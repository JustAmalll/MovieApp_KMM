package dev.amal.movieapp.feature_movie_list.domain.repository

import dev.amal.movieapp.feature_movie_list.domain.model.Genre
import dev.amal.movieapp.feature_movie_list.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getGenreMovieList(): List<Genre>
    suspend fun searchMovie(page: Int, query: String): List<Movie>
}