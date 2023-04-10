package dev.amal.movieapp.android.feature_movie_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.amal.movieapp.feature_favorite_movies.domain.repository.FavoriteMoviesRepository
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import dev.amal.movieapp.feature_movie_list.presentation.MovieUIEvent
import dev.amal.movieapp.feature_movie_list.presentation.MovieViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) : ViewModel() {

    private val viewModel by lazy {
        MovieViewModel(
            movieRepository = movieRepository,
            favoriteMoviesRepository = favoriteMoviesRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: MovieUIEvent) {
        viewModel.onEvent(event)
    }

    fun getGenreById(genresId: List<Int>): String {
        return viewModel.getGenreById(genresId)
    }
}