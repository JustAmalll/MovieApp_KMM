package dev.amal.movieapp.feature_movie_list.presentation

import dev.amal.movieapp.core.domain.util.toCommonStateFlow
import dev.amal.movieapp.core.utils.Resource
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import dev.amal.movieapp.feature_movie_list.pagination.DefaultPaginator
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

    private val paginator = DefaultPaginator(
        initialKey = state.value.page,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextPage ->
            movieRepository.getPopularMovies(page = nextPage)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = { message ->
            _state.update { it.copy(error = message) }
        },
        onSuccess = { items, newKey ->
            _state.value = state.value.copy(
                popularMovies = state.value.popularMovies + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
        getGenreMovieList()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun getGenreMovieList() {
        viewModelScope.launch {
            when (val result = movieRepository.getGenreMovieList()) {
                is Resource.Success -> _state.update {
                    it.copy(genres = result.data ?: emptyList())
                }
                is Resource.Error -> _state.update {
                    it.copy(error = result.message ?: "An unknown error occurred")
                }
            }
        }
    }

    fun getGenreById(genresId: List<Int>): String {
        val filtered = state.value.genres.filter { it.id in genresId }
        return filtered.joinToString { it.name }
    }
}