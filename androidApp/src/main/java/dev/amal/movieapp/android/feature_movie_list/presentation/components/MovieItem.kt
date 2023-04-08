package dev.amal.movieapp.android.feature_movie_list.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.amal.movieapp.android.R

@Composable
fun MovieItem() {
    Row(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            modifier = Modifier
                .width(80.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Avatar: The Way of Water",
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp
            )
            Text(
                text = "Science Fiction, Adventure, Action",
                modifier = Modifier.alpha(0.6f),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
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
    Spacer(modifier = Modifier.height(12.dp))
}