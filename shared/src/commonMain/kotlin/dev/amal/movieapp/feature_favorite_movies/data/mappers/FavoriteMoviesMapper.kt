package dev.amal.movieapp.feature_favorite_movies.data.mappers

import database.FavoriteMoviesEntity
import dev.amal.movieapp.feature_favorite_movies.domain.model.FavoriteMovie
import dev.amal.movieapp.feature_movie_list.presentation.MovieItemState

fun FavoriteMoviesEntity.toFavoriteMovie(): FavoriteMovie = FavoriteMovie(
    id = id,
    backdrop_path = backdrop_path,
    genres = genres,
    title = title,
    vote_average = vote_average
)

fun MovieItemState.toFavoriteMovie(): FavoriteMovie = FavoriteMovie(
    id = id,
    backdrop_path = backdrop_path,
    genres = genres,
    title = title,
    vote_average = vote_average
)

fun FavoriteMovie.toMovieItemState(): MovieItemState = MovieItemState(
    id = id,
    backdrop_path = backdrop_path,
    genres = genres,
    title = title,
    vote_average = vote_average,
    isFavoriteMovie = true
)