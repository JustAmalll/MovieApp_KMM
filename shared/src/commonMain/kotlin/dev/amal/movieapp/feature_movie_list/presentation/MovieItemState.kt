package dev.amal.movieapp.feature_movie_list.presentation

data class MovieItemState(
    val id: Long,
    val backdrop_path: String?,
    val genres: String,
    val title: String,
    val vote_average: Double,
    var isFavoriteMovie: Boolean = false
)