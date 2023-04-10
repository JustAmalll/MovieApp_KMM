package dev.amal.movieapp.feature_favorite_movies.presentation

sealed interface FavoriteMoviesUIEvent {
    data class OnRemoveFromFavorites(val movieId: Long) : FavoriteMoviesUIEvent
}