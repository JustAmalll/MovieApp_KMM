package dev.amal.movieapp.android.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.amal.movieapp.android.R
import dev.amal.movieapp.android.navigation.Screen

@Composable
fun NavigationBarScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    val bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.MovieListScreen.route,
            icon = Icons.Default.Movie,
            contentDescription = stringResource(R.string.movies)
        ),
        BottomNavItem(
            route = Screen.FavoritesScreen.route,
            icon = Icons.Default.Favorite,
            contentDescription = stringResource(R.string.favorites)
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val selected = item.route == backStackEntry.value?.destination?.route

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.contentDescription
                            )
                        },
                        label = { Text(text = item.contentDescription) },
                        selected = selected,
                        onClick = {
                            if (navController.currentDestination?.route != item.route) {
                                navController.navigate(item.route) { launchSingleTop = true }
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}