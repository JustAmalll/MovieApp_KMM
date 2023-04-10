package dev.amal.movieapp.feature_movie_list.data.repository

import dev.amal.movieapp.core.utils.Resource
import dev.amal.movieapp.feature_movie_list.data.remote.dto.GenreListDto
import dev.amal.movieapp.feature_movie_list.data.remote.dto.MovieDto
import dev.amal.movieapp.feature_movie_list.data.remote.dto.toGenre
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

    override suspend fun getPopularMovies(page: Int): Resource<List<Movie>> {
        val result = client.get("movie/popular") {
            parameter("page", page)
        }

        val response = result.body<MovieDto>()
        val popularMovies = response.results.map { it.toMovie() }
        return Resource.Success(popularMovies)
    }

    override suspend fun getGenreMovieList(): Resource<List<Genre>> {
        val result = client.get("genre/movie/list")

        val response = result.body<GenreListDto>()
        val genres = response.genres.map { it.toGenre() }
        return Resource.Success(genres)
    }

    override suspend fun searchMovie(page: Int, query: String): Resource<List<Movie>> {
        val result = client.get("search/movie") {
            parameter("query", query)
            parameter("page", page)
        }

        val response = result.body<MovieDto>()
        val searchedMovie = response.results.map { it.toMovie() }
        return Resource.Success(searchedMovie)
    }
}