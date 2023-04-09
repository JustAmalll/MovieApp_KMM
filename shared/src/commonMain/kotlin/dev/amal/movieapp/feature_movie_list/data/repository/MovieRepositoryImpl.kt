package dev.amal.movieapp.feature_movie_list.data.repository

import dev.amal.movieapp.core.utils.Constants.API_KEY
import dev.amal.movieapp.core.utils.Constants.BASE_URL
import dev.amal.movieapp.core.utils.Resource
import dev.amal.movieapp.feature_movie_list.data.remote.dto.*
import dev.amal.movieapp.feature_movie_list.domain.model.Genre
import dev.amal.movieapp.feature_movie_list.domain.model.Movie
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MovieRepositoryImpl(
    private val client: HttpClient,
) : MovieRepository {

    // TODO handle network errors
    override suspend fun getPopularMovies(page: Int): Resource<List<Movie>> {
        val result = client.get("$BASE_URL/movie/popular") {
            parameter("api_key", API_KEY)
            parameter("page", page)
        }

        val response = result.body<MovieDto>()
        val popularMovies = response.results.map { it.toMovie() }
        return Resource.Success(popularMovies)
    }

    // TODO handle network errors
    override suspend fun getGenreMovieList(): Resource<List<Genre>> {
        val result = client.get("$BASE_URL/genre/movie/list") {
            parameter("api_key", API_KEY)
        }

        val response = result.body<GenreListDto>()
        val genres = response.genres.map { it.toGenre() }
        return Resource.Success(genres)
    }
}