package dev.amal.movieapp.android.navigation

sealed class Screen(val route: String) {
    object MovieListScreen : Screen("movie_list_screen")
    object FavoritesScreen : Screen("favorites_screen")
}