package dev.amal.movieapp.feature_movie_list.domain.repository

import dev.amal.movieapp.core.utils.Resource
import dev.amal.movieapp.feature_movie_list.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): Resource<List<Movie>>
}