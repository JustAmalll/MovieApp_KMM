package dev.amal.movieapp.android.feature_movie_list.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.amal.movieapp.android.feature_movie_list.presentation.components.MovieItem
import dev.amal.movieapp.android.feature_movie_list.presentation.components.SearchAppBar
import dev.amal.movieapp.feature_movie_list.presentation.MovieItemState
import dev.amal.movieapp.feature_movie_list.presentation.MovieListState
import dev.amal.movieapp.feature_movie_list.presentation.MovieUIEvent
import dev.amal.movieapp.feature_movie_list.presentation.MovieUIEvent.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    state: MovieListState,
    onEvent: (MovieUIEvent) -> Unit,
    getGenreById: (List<Int>) -> String
) {
    var showSearchingTopBar by remember { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

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

    LaunchedEffect(key1 = state.error) {
        state.error?.let { error ->
            snackBarHostState.showSnackbar(message = error)
            onEvent(OnErrorSeen)
        }
    }

    var popularMovies by remember(state.popularMovies) {
        mutableStateOf(
            state.popularMovies.map { movie ->
                MovieItemState(
                    id = movie.id,
                    backdrop_path = movie.backdrop_path,
                    genres = getGenreById(movie.genre_ids),
                    title = movie.title,
                    vote_average = movie.vote_average,
                    isFavoriteMovie = state.favoriteMovieIds.any { favoriteMovieId ->
                        favoriteMovieId == movie.id
                    }
                )
            }
        )
    }

    var searchedMovies by remember(state.searchedMovies) {
        mutableStateOf(
            state.searchedMovies.map { movie ->
                MovieItemState(
                    id = movie.id,
                    backdrop_path = movie.backdrop_path,
                    genres = getGenreById(movie.genre_ids),
                    title = movie.title,
                    vote_average = movie.vote_average,
                    isFavoriteMovie = state.favoriteMovieIds.any { favoriteMovieId ->
                        favoriteMovieId == movie.id
                    }
                )
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
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
                        focusManager.clearFocus()

                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
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
            state = listState,
            contentPadding = PaddingValues(vertical = 18.dp)
        ) {
            val listToDisplay = searchedMovies.ifEmpty { popularMovies }

            itemsIndexed(listToDisplay) { index, movie ->
                if (index >= listToDisplay.size - 5 && !state.endReached && !state.isLoading) {
                    if (searchedMovies.isEmpty()) onEvent(LoadNextMovies)
                    else onEvent(LoadNextSearchedMovies)
                }
                MovieItem(
                    movie = movie,
                    onAddToFavorites = {
                        onEvent(OnAddToFavorites(movie))

                        if (searchedMovies.isEmpty()) {
                            popularMovies = popularMovies.mapIndexed { i, item ->
                                if (index == i) item.copy(isFavoriteMovie = !item.isFavoriteMovie)
                                else item
                            }
                        } else searchedMovies = searchedMovies.mapIndexed { i, item ->
                            if (index == i) item.copy(isFavoriteMovie = !item.isFavoriteMovie)
                            else item
                        }
                    },
                    onRemoveFromFavorites = {
                        onEvent(OnRemoveFromFavorites(movie.id))

                        if (searchedMovies.isEmpty()) {
                            popularMovies = popularMovies.mapIndexed { i, item ->
                                if (index == i) item.copy(isFavoriteMovie = !item.isFavoriteMovie)
                                else item
                            }
                        } else searchedMovies = searchedMovies.mapIndexed { i, item ->
                            if (index == i) item.copy(isFavoriteMovie = !item.isFavoriteMovie)
                            else item
                        }
                    }
                )
                if (index < listToDisplay.lastIndex) {
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
        state = MovieListState(),
        onEvent = {},
        getGenreById = { "" }
    )
}