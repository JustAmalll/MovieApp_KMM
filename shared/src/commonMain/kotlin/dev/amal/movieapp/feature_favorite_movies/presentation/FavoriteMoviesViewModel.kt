package dev.amal.movieapp.feature_favorite_movies.presentation

import dev.amal.movieapp.core.domain.util.toCommonStateFlow
import dev.amal.movieapp.feature_favorite_movies.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(FavoriteMoviesState())
    val state = _state.toCommonStateFlow()

    init {
        getFavoriteMovies()
    }

    fun onEvent(event: FavoriteMoviesUIEvent) {
        when (event) {
            is FavoriteMoviesUIEvent.RemoveFromFavorites -> {
                removeFromFavorites(event.movieId)
            }
        }
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            favoriteMoviesRepository.getFavoriteMovies().collectLatest {
                _state.value = state.value.copy(favoriteMovies = it)
            }
        }
    }

    private fun removeFromFavorites(movieId: Long) {
        viewModelScope.launch {
            favoriteMoviesRepository.removeFromFavorites(movieId)
        }
    }
}