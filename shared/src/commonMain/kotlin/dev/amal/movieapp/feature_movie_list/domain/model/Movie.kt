package dev.amal.movieapp.feature_movie_list.domain.model

data class Movie(
    val id: Long,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val title: String,
    val vote_average: Double
)