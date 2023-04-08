package dev.amal.movieapp.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.amal.movieapp.android.feature_favorites.presentation.FavoritesScreen
import dev.amal.movieapp.android.feature_movie_list.presentation.MovieListScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MovieListScreen.route
    ) {
        composable(route = Screen.MovieListScreen.route) {
            MovieListScreen()
        }
        composable(route = Screen.FavoritesScreen.route) {
            FavoritesScreen()
        }
    }
}