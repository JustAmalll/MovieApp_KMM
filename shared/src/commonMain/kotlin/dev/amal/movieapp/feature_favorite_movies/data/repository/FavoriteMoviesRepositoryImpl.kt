package dev.amal.movieapp.feature_favorite_movies.data.repository

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.amal.movieapp.core.domain.util.CommonFlow
import dev.amal.movieapp.core.domain.util.toCommonFlow
import dev.amal.movieapp.database.FavoriteMoviesDatabase
import dev.amal.movieapp.feature_favorite_movies.data.mappers.toFavoriteMovie
import dev.amal.movieapp.feature_favorite_movies.domain.model.FavoriteMovie
import dev.amal.movieapp.feature_favorite_movies.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.flow.map

class FavoriteMoviesRepositoryImpl(
    db: FavoriteMoviesDatabase
) : FavoriteMoviesRepository {
    private val queries = db.favoriteMoviesQueries

    override suspend fun getFavoriteMovies(): CommonFlow<List<FavoriteMovie>> {
        return queries
            .getFavoriteMovies()
            .asFlow()
            .mapToList()
            .map { favoriteMovies -> favoriteMovies.map { it.toFavoriteMovie() } }
            .toCommonFlow()
    }

    override suspend fun addToFavorites(movie: FavoriteMovie) {
        queries.insertFavoriteMoviesEntity(
            id = movie.id,
            backdrop_path = movie.backdrop_path,
            genres = movie.genres,
            title = movie.title,
            vote_average = movie.vote_average
        )
    }

    override suspend fun removeFromFavorites(movieId: Long) {
        queries.removeFromFavorites(id = movieId)
    }
}