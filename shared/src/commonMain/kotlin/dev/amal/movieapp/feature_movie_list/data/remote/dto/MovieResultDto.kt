package dev.amal.movieapp.feature_movie_list.data.remote.dto

import dev.amal.movieapp.feature_movie_list.domain.model.Movie

@kotlinx.serialization.Serializable
data class MovieResultDto(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieResultDto.toMovie(): Movie = Movie(
    id = id,
    backdrop_path = backdrop_path,
    genre_ids = genre_ids,
    release_date = release_date,
    title = title,
    vote_average = vote_average
)