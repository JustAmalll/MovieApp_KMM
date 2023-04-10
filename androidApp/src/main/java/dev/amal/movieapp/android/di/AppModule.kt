package dev.amal.movieapp.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amal.movieapp.core.data.remote.HttpClientFactory
import dev.amal.movieapp.feature_movie_list.data.local.DatabaseDriverFactory
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClientFactory().create()

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver =
        DatabaseDriverFactory(app).create()
}