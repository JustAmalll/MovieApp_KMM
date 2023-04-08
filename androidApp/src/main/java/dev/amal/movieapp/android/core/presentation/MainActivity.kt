package dev.amal.movieapp.android.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.amal.movieapp.android.core.presentation.components.NavigationBarScaffold
import dev.amal.movieapp.android.core.presentation.ui.theme.MovieAppTheme
import dev.amal.movieapp.android.navigation.SetupNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                val navController = rememberNavController()

                NavigationBarScaffold(navController = navController) {
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}