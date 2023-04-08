package dev.amal.movieapp.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amal.movieapp.feature_movie_list.data.repository.MovieRepositoryImpl
import dev.amal.movieapp.feature_movie_list.domain.repository.MovieRepository
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieRepository(httpClient: HttpClient): MovieRepository =
        MovieRepositoryImpl(httpClient)
}