package dev.amal.movieapp.feature_movie_list.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dev.amal.movieapp.database.FavoriteMoviesDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver =
        AndroidSqliteDriver(FavoriteMoviesDatabase.Schema, context, "favoriteMovies.db")
}