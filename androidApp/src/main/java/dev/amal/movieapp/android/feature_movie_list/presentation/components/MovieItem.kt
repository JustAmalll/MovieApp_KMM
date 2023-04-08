package dev.amal.movieapp.android.feature_movie_list.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.amal.movieapp.android.R
import dev.amal.movieapp.feature_movie_list.domain.model.Movie

@Composable
fun MovieItem(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            model = "https://image.tmdb.org/t/p/w500" + movie.backdrop_path,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
                Text(
                    text = "Science Fiction, Adventure, Action",
                    modifier = Modifier.alpha(0.6f),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "Star"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "7.7", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }
        }
    }
    Divider(modifier = Modifier.padding(vertical = 24.dp))
}

