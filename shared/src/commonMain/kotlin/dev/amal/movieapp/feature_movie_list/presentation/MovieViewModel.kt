package dev.amal.movieapp.feature_movie_list.presentation

import dev.amal.movieapp.core.domain.util.toCommonStateFlow
import dev.amal.movieapp.core.utils.Resource
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(MovieState())
    val state = _state.toCommonStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            when (val result = movieRepository.getPopularMovies()) {
                is Resource.Success -> _state.update {
                    it.copy(popularMovies = result.data ?: emptyList())
                }
                is Resource.Error -> _state.update {
                    it.copy(error = result.message ?: "An unknown error occurred")
                }
            }
        }
    }
}