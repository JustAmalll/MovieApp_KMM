package dev.amal.movieapp.feature_movie_list.domain.model

@kotlinx.serialization.Serializable
data class Genre(
    val id: Int,
    val name: String
)