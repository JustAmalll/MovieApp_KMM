package dev.amal.movieapp.android.feature_movie_list.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.amal.movieapp.android.R
import dev.amal.movieapp.android.feature_movie_list.presentation.components.LoadingView
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
    var showSearchingContent by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    val focusManager = LocalFocusManager.current

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()
    val statusBarColor = MaterialTheme.colorScheme.background
    val navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor, darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor, darkIcons = !darkTheme
        )
    }

    LaunchedEffect(key1 = state.error) {
        state.error?.let { error ->
            snackBarHostState.showSnackbar(message = error)
            onEvent(OnErrorSeen)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            AnimatedContent(
                targetState = showSearchingContent
            ) { showSearchingTopBar ->
                if (showSearchingTopBar) SearchAppBar(
                    text = state.searchText,
                    onValueChange = { onEvent(OnSearchTextChanged(it)) },
                    onCloseClicked = {
                        showSearchingContent = false
                        onEvent(OnSearchCloseClicked)
                    },
                    onSearchClicked = {
                        onEvent(OnSearchClicked)
                        focusManager.clearFocus()
                        scope.launch { lazyListState.scrollToItem(index = 0) }
                    }
                ) else TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.popular_movies),
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                    },
                    actions = {
                        IconButton(onClick = { showSearchingContent = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.search)
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->

        AnimatedContent(
            targetState = state.isGenresLoading || state.isMoviesLoading
        ) { isLoading ->
            if (isLoading) {
                LoadingView()
            } else {
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    state = lazyListState,
                    contentPadding = PaddingValues(vertical = 18.dp)
                ) {
                    val listToDisplay = searchedMovies.ifEmpty { popularMovies }

                    itemsIndexed(listToDisplay) { index, movie ->
                        if (index >= listToDisplay.size - 5 && !state.isNextItemsLoading) {
                            if (searchedMovies.isEmpty() && !state.popularMoviesEndReached) {
                                onEvent(LoadNextMovies)
                            } else if (!state.searchedMoviesEndReached) {
                                onEvent(LoadNextSearchedMovies)
                            }
                        }
                        MovieItem(
                            movie = movie,
                            onLikeButtonClicked = {
                                if (movie.isFavoriteMovie) onEvent(RemoveFromFavorites(movie.id))
                                else onEvent(AddToFavorites(movie = movie))

                                if (searchedMovies.isEmpty()) popularMovies = onLikeButtonClick(
                                    items = popularMovies, itemIndex = index
                                )
                                else searchedMovies = onLikeButtonClick(
                                    items = searchedMovies, itemIndex = index
                                )
                            }
                        )
                        if (index < listToDisplay.lastIndex) {
                            Divider(modifier = Modifier.padding(vertical = 24.dp))
                        }
                    }
                    item {
                        AnimatedVisibility(visible = state.isNextItemsLoading) {
                            Row(
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
        }
    }
}

fun onLikeButtonClick(
    items: List<MovieItemState>, itemIndex: Int
): List<MovieItemState> = items.mapIndexed { index, movieItemState ->
    if (itemIndex == index) movieItemState.copy(isFavoriteMovie = !movieItemState.isFavoriteMovie)
    else movieItemState
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