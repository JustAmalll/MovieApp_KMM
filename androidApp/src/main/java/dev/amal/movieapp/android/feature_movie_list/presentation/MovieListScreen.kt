package dev.amal.movieapp.android.feature_movie_list.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.amal.movieapp.android.feature_movie_list.presentation.components.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen() {
    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColorAtElevation = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor, darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = surfaceColorAtElevation, darkIcons = !darkTheme
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Popular Movies") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(count = 6) {
                MovieItem()
            }
        }
    }
}

