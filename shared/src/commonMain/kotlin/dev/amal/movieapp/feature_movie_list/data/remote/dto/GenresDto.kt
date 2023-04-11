package dev.amal.movieapp.feature_movie_list.data.remote.dto

import dev.amal.movieapp.feature_movie_list.domain.model.Genre

@kotlinx.serialization.Serializable
data class GenresDto(
    val genres: List<Genre>
)