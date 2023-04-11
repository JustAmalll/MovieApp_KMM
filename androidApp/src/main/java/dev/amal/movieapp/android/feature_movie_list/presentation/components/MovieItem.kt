package dev.amal.movieapp.android.feature_movie_list.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.amal.movieapp.android.R
import dev.amal.movieapp.core.utils.Constants.IMAGES_URL
import dev.amal.movieapp.feature_movie_list.presentation.MovieItemState

@Composable
fun MovieItem(
    movie: MovieItemState,
    onLikeButtonClicked: () -> Unit
) {
    var isImageLoading by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Card(modifier = Modifier.height(200.dp)) {
            Box(contentAlignment = Alignment.Center) {
                if (isImageLoading) CircularProgressIndicator()
                else if (movie.backdrop_path == null) Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = stringResource(R.string.error)
                )
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    model = IMAGES_URL + movie.backdrop_path,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    onLoading = { isImageLoading = true },
                    onSuccess = { isImageLoading = false },
                    onError = { isImageLoading = false },
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
                Text(
                    text = movie.genres,
                    modifier = Modifier.alpha(0.6f),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = stringResource(R.string.star)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.vote_average.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            IconButton(onClick = onLikeButtonClicked) {
                if (movie.isFavoriteMovie) Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorite),
                    tint = Color.Red
                ) else Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_border)
                )
            }
        }
    }
}

