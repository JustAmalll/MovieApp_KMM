package dev.amal.movieapp.feature_favorite_movies.domain.model

data class FavoriteMovie(
    val id: Long,
    val backdrop_path: String?,
    val genres: String,
    val release_date: String?,
    val title: String,
    val vote_average: Double
)