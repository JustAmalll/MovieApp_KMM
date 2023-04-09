package dev.amal.movieapp.feature_movie_list.domain.repository

import dev.amal.movieapp.core.utils.Resource
import dev.amal.movieapp.feature_movie_list.domain.model.Genre
import dev.amal.movieapp.feature_movie_list.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Resource<List<Movie>>
    suspend fun getGenreMovieList(): Resource<List<Genre>>
}