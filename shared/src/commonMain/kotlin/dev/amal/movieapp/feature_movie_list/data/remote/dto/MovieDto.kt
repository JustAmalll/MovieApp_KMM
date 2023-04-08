package dev.amal.movieapp.feature_movie_list.data.remote.dto

@kotlinx.serialization.Serializable
data class MovieDto(
    val page: Int,
    val results: List<MovieResultDto>,
    val total_pages: Int,
    val total_results: Int
)