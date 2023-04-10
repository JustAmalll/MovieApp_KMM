package dev.amal.movieapp.feature_favorite_movies.domain.repository

import dev.amal.movieapp.core.domain.util.CommonFlow
import dev.amal.movieapp.feature_favorite_movies.domain.model.FavoriteMovie

interface FavoriteMoviesRepository {
    suspend fun getFavoriteMovies(): CommonFlow<List<FavoriteMovie>>
    suspend fun addToFavorites(movie: FavoriteMovie)
    suspend fun removeFromFavorites(movieId: Long)
}