package dev.amal.movieapp.feature_movie_list.presentation

sealed interface MovieUIEvent {
    data class OnSearchTextChanged(val value: String) : MovieUIEvent
    object OnSearchClicked : MovieUIEvent
    object OnSearchCloseClicked : MovieUIEvent
    object LoadNextMovies : MovieUIEvent
    object LoadNextSearchedMovies : MovieUIEvent
    data class AddToFavorites(val movie: MovieItemState) : MovieUIEvent
    data class RemoveFromFavorites(val movieId: Long) : MovieUIEvent
    object OnErrorSeen : MovieUIEvent
}