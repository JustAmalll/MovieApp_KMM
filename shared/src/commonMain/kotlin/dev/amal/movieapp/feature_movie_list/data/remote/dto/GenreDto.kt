package dev.amal.movieapp.feature_movie_list.data.remote.dto

import dev.amal.movieapp.feature_movie_list.domain.model.Genre

@kotlinx.serialization.Serializable
data class GenreDto(
    val id: Int,
    val name: String
)

fun GenreDto.toGenre(): Genre = Genre(
    id = id, name = name
)