package dev.amal.movieapp.android.feature_movie_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import dev.amal.movieapp.feature_movie_list.presentation.MovieViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val viewModel by lazy {
        MovieViewModel(
            movieRepository = movieRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state
}