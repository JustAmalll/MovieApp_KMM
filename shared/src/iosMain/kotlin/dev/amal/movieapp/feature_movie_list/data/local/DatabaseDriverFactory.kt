package dev.amal.movieapp.feature_movie_list.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import dev.amal.movieapp.database.FavoriteMoviesDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(
        FavoriteMoviesDatabase.Schema, "favoriteMovies.db"
    )
}