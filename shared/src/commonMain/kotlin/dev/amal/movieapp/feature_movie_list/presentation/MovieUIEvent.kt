package dev.amal.movieapp.feature_movie_list.presentation

sealed interface MovieUIEvent {
    data class OnSearchTextChanged(val value: String): MovieUIEvent
    object OnSearchClicked: MovieUIEvent
    object OnSearchCloseClicked: MovieUIEvent
    object LoadNextItems: MovieUIEvent
}