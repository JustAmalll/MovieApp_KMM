package dev.amal.movieapp.android.feature_favorite_movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.amal.movieapp.feature_favorite_movies.domain.repository.FavoriteMoviesRepository
import dev.amal.movieapp.feature_favorite_movies.presentation.FavoriteMoviesUIEvent
import dev.amal.movieapp.feature_favorite_movies.presentation.FavoriteMoviesViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidFavoriteMoviesViewModel @Inject constructor(
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) : ViewModel() {

    private val viewModel by lazy {
        FavoriteMoviesViewModel(
            favoriteMoviesRepository = favoriteMoviesRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: FavoriteMoviesUIEvent) {
        viewModel.onEvent(event)
    }
}