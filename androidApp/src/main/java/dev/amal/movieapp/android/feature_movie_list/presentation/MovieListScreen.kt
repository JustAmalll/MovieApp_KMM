package dev.amal.movieapp.android.feature_movie_list.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.amal.movieapp.android.feature_movie_list.presentation.components.MovieItem
import dev.amal.movieapp.android.feature_movie_list.presentation.components.SearchAppBar
import dev.amal.movieapp.feature_movie_list.presentation.MovieState
import dev.amal.movieapp.feature_movie_list.presentation.MovieUIEvent
import dev.amal.movieapp.feature_movie_list.presentation.MovieUIEvent.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MovieListScreen(
    state: MovieState,
    onEvent: (MovieUIEvent) -> Unit,
    getGenreById: (List<Int>) -> String
) {
    var showSearchingTopBar by remember { mutableStateOf(false) }

    val keyBoardController = LocalSoftwareKeyboardController.current

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
            AnimatedContent(targetState = showSearchingTopBar) { shouldShowSearchingTopBar ->
                if (shouldShowSearchingTopBar) SearchAppBar(
                    text = state.searchText,
                    onValueChange = { onEvent(OnSearchTextChanged(it)) },
                    onCloseClicked = {
                        showSearchingTopBar = false
                        onEvent(OnSearchCloseClicked)
                    },
                    onSearchClicked = {
                        onEvent(OnSearchClicked)
                        keyBoardController?.hide()
                    }
                )
                else TopAppBar(
                    title = {
                        Text(
                            text = "Popular Movies",
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                    },
                    actions = {
                        IconButton(onClick = { showSearchingTopBar = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 18.dp)
        ) {
            val listToShow = state.searchedMovies.ifEmpty { state.popularMovies }

            itemsIndexed(listToShow) { index, movie ->
                if (index >= listToShow.size - 1 && !state.endReached && !state.isLoading) {
                    onEvent(LoadNextItems)
                }
                MovieItem(
                    movie = movie,
                    getGenreById = { getGenreById(movie.genre_ids) }
                )
                if (index < listToShow.lastIndex) {
                    Divider(modifier = Modifier.padding(vertical = 24.dp))
                }
            }
            item {
                if (state.isLoading) Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    MovieListScreen(
        state = MovieState(),
        onEvent = {},
        getGenreById = { "" }
    )
}