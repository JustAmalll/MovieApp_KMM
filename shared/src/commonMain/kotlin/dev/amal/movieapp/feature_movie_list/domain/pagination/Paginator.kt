package dev.amal.movieapp.feature_movie_list.domain.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}