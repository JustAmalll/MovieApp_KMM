package dev.amal.movieapp.android.feature_favorite_movies.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.amal.movieapp.android.R
import dev.amal.movieapp.android.feature_movie_list.presentation.components.MovieItem
import dev.amal.movieapp.feature_favorite_movies.data.mappers.toMovieItemState
import dev.amal.movieapp.feature_favorite_movies.presentation.FavoriteMoviesState
import dev.amal.movieapp.feature_favorite_movies.presentation.FavoriteMoviesUIEvent
import dev.amal.movieapp.feature_favorite_movies.presentation.FavoriteMoviesUIEvent.RemoveFromFavorites

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMoviesScreen(
    state: FavoriteMoviesState,
    onEvent: (FavoriteMoviesUIEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorite_movies),
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 18.dp)
        ) {
            itemsIndexed(state.favoriteMovies) { index, movie ->
                MovieItem(
                    movie = movie.toMovieItemState(),
                    onLikeButtonClicked = { onEvent(RemoveFromFavorites(movie.id)) }
                )
                if (index < state.favoriteMovies.lastIndex) {
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