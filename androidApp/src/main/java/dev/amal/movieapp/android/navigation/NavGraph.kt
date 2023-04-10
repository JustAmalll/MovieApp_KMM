package dev.amal.movieapp.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.amal.movieapp.android.feature_favorite_movies.presentation.AndroidFavoriteMoviesViewModel
import dev.amal.movieapp.android.feature_favorite_movies.presentation.FavoritesScreen
import dev.amal.movieapp.android.feature_movie_list.presentation.AndroidMovieViewModel
import dev.amal.movieapp.android.feature_movie_list.presentation.MovieListScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MovieListScreen.route
    ) {
        composable(route = Screen.MovieListScreen.route) {
            val viewModel = hiltViewModel<AndroidMovieViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            MovieListScreen(
                state = state,
                onEvent = viewModel::onEvent,
                getGenreById = viewModel::getGenreById
            )
        }
        composable(route = Screen.FavoritesScreen.route) {
            val viewModel = hiltViewModel<AndroidFavoriteMoviesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            FavoritesScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
}