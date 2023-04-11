package dev.amal.movieapp.feature_movie_list.data.repository

import dev.amal.movieapp.core.utils.handleHttpStatusCode
import dev.amal.movieapp.core.utils.handleNetworkException
import dev.amal.movieapp.feature_favorite_movies.domain.NetworkError
import dev.amal.movieapp.feature_favorite_movies.domain.NetworkException
import dev.amal.movieapp.feature_movie_list.data.remote.dto.GenresDto
import dev.amal.movieapp.feature_movie_list.data.remote.dto.MovieDto
import dev.amal.movieapp.feature_movie_list.data.remote.dto.toMovie
import dev.amal.movieapp.feature_movie_list.domain.model.Genre
import dev.amal.movieapp.feature_movie_list.domain.model.Movie
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MovieRepositoryImpl(
    private val client: HttpClient
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val result = try {
            client.get("movie/popular") {
                parameter("page", page)
            }
        } catch (exception: Exception) {
            val error = exception.handleNetworkException()
            throw NetworkException(error)
        }

        val error = result.status.value.handleHttpStatusCode()
        if (error != null) throw NetworkException(error)

        return try {
            val response = result.body<MovieDto>()
            response.results.map { it.toMovie() }
        } catch (exception: Exception) {
            throw NetworkException(NetworkError.SERVER_ERROR)
        }
    }

    override suspend fun getGenreMovieList(): List<Genre> {
        val result = try {
            client.get("genre/movie/list")
        } catch (exception: Exception) {
            val error = exception.handleNetworkException()
            throw NetworkException(error)
        }

        val error = result.status.value.handleHttpStatusCode()
        if (error != null) throw NetworkException(error)

        return try {
            result.body<GenresDto>().genres
        } catch (exception: Exception) {
            throw NetworkException(NetworkError.SERVER_ERROR)
        }
    }

    override suspend fun searchMovie(page: Int, query: String): List<Movie> {
        val result = try {
            client.get("search/movie") {
                parameter("query", query)
                parameter("page", page)
            }
        } catch (exception: Exception) {
            val error = exception.handleNetworkException()
            throw NetworkException(error)
        }

        val error = result.status.value.handleHttpStatusCode()
        if (error != null) throw NetworkException(error)

        return try {
            val response = result.body<MovieDto>()
            response.results.map { it.toMovie() }
        } catch (exception: Exception) {
            throw NetworkException(NetworkError.SERVER_ERROR)
        }
    }
}