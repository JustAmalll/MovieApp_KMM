package dev.amal.movieapp.feature_movie_list.presentation

import dev.amal.movieapp.core.domain.util.toCommonStateFlow
import dev.amal.movieapp.core.utils.Resource
import dev.amal.movieapp.feature_favorite_movies.data.mappers.toFavoriteMovie
import dev.amal.movieapp.feature_favorite_movies.domain.repository.FavoriteMoviesRepository
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import dev.amal.movieapp.feature_movie_list.pagination.DefaultPaginator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(MovieListState())
    val state = _state.toCommonStateFlow()

    private val popularMoviesPaginator = DefaultPaginator(
        initialKey = state.value.moviePage,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextPage ->
            movieRepository.getPopularMovies(page = nextPage)
        },
        getNextKey = {
            state.value.moviePage + 1
        },
        onError = { message ->
            _state.update { it.copy(error = message) }
        },
        onSuccess = { items, newKey ->
            _state.update {
                it.copy(
                    popularMovies = state.value.popularMovies + items,
                    moviePage = newKey,
                    endReached = items.isEmpty()
                )
            }
        }
    )

    private val searchPaginator = DefaultPaginator(
        initialKey = state.value.searchPage,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextPage ->
            movieRepository.searchMovie(query = state.value.searchText, page = nextPage)
        },
        getNextKey = {
            state.value.searchPage + 1
        },
        onError = { message ->
            _state.update { it.copy(error = message) }
        },
        onSuccess = { items, newKey ->
            _state.update {
                it.copy(
                    searchedMovies = state.value.searchedMovies + items,
                    searchPage = newKey,
                    endReached = items.isEmpty()
                )
            }
        }
    )

    init {
        getGenreMovieList()
        loadNextMovies()
        getFavoriteMovies()
    }

    fun onEvent(event: MovieUIEvent) {
        when (event) {
            MovieUIEvent.LoadNextMovies -> loadNextMovies()
            is MovieUIEvent.OnSearchTextChanged -> _state.update {
                it.copy(searchText = event.value)
            }
            MovieUIEvent.OnSearchClicked -> {
                _state.update { it.copy(searchedMovies = emptyList(), searchPage = 1) }
                searchPaginator.reset()
                loadNextSearchedMovies()
            }
            MovieUIEvent.LoadNextSearchedMovies -> loadNextSearchedMovies()
            MovieUIEvent.OnSearchCloseClicked -> _state.update {
                it.copy(searchedMovies = emptyList())
            }
            is MovieUIEvent.OnAddToFavorites -> addToFavorites(event.movie)
            is MovieUIEvent.OnRemoveFromFavorites -> removeFromFavorites(event.movieId)
            MovieUIEvent.OnErrorSeen -> _state.update { it.copy(error = null) }
        }
    }

    private fun addToFavorites(movie: MovieItemState) {
        viewModelScope.launch {
            favoriteMoviesRepository.addToFavorites(movie.toFavoriteMovie())
        }
    }

    private fun removeFromFavorites(movieId: Long) {
        viewModelScope.launch {
            favoriteMoviesRepository.removeFromFavorites(movieId)
        }
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            favoriteMoviesRepository.getFavoriteMovies().collectLatest { favoriteMovies ->
                val favoriteMovieIds = favoriteMovies.map { favoriteMovie -> favoriteMovie.id }
                _state.update { it.copy(favoriteMovieIds = favoriteMovieIds) }
            }
        }
    }

    private fun loadNextMovies() {
        viewModelScope.launch {
            popularMoviesPaginator.loadNextItems()
        }
    }

    private fun loadNextSearchedMovies() {
        viewModelScope.launch {
            searchPaginator.loadNextItems()
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