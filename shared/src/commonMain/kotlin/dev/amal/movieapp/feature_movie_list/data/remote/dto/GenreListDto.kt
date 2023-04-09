package dev.amal.movieapp.feature_movie_list.data.remote.dto

@kotlinx.serialization.Serializable
data class GenreListDto(
    val genres: List<GenreDto>
)