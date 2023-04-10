package dev.amal.movieapp.android.di

import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amal.movieapp.database.FavoriteMoviesDatabase
import dev.amal.movieapp.feature_favorite_movies.data.repository.FavoriteMoviesRepositoryImpl
import dev.amal.movieapp.feature_favorite_movies.domain.repository.FavoriteMoviesRepository
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteMoviesModule {

    @Provides
    @Singleton
    fun provideFavoriteMoviesRepository(driver: SqlDriver): FavoriteMoviesRepository =
        FavoriteMoviesRepositoryImpl(FavoriteMoviesDatabase(driver))
}