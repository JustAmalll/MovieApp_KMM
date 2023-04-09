package dev.amal.movieapp.feature_movie_list.domain.model

data class Movie(
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val release_date: String,
    val title: String,
    val vote_average: Double
)